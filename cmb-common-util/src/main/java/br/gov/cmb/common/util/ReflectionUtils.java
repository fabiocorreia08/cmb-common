package br.gov.cmb.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import br.gov.cmb.common.exception.runtime.CMBReflectionException;
import br.gov.cmb.common.util.anotacao.Lookup;

public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static <T, A extends Annotation>List<Method> recuperarMetodosAnotadosCom(Class<T> classe, Class<A> anotacao) {
        return CollectionUtils.castList(recuperarElementosAnotadosCom(anotacao, classe.getDeclaredMethods()), Method.class);
    }

    public static <T, A extends Annotation>List<Field> recuperarCamposAnotadosCom(Class<T> classe, Class<A> anotacao) {
        return CollectionUtils.castList(recuperarElementosAnotadosCom(anotacao, classe.getDeclaredFields()), Field.class);
    }
    
    private static <T extends Annotation>List<AccessibleObject> recuperarElementosAnotadosCom(Class<T> anotacao, AccessibleObject[] elementos) {
        List<AccessibleObject> camposAnotados = new ArrayList<>();

        if (elementos != null) {
            for (AccessibleObject elemento : elementos) {
                if (elemento.isAnnotationPresent(anotacao)) {
                    camposAnotados.add(elemento);
                }
            }
        }

        return camposAnotados;
    }
    
    
    public static Object recuperarValorCampo(Object objeto, String nomeDoCampo) {
    	return recuperarValorCampo(objeto, nomeDoCampo, objeto.getClass());
    }
    
    private static Object recuperarValorCampo(Object objeto, String nomeDoCampo,  Class<?> classeOrigem) {
    	try{
			Field campo = classeOrigem.getDeclaredField(nomeDoCampo);
            campo.setAccessible(true);
            return campo.get(objeto);
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            throw new CMBReflectionException(e);
        }
    }
    
    @SuppressWarnings("deprecation")
	public static  <T> T instanciar(Class<T> classe) {
        try{
            T instancia = classe.newInstance();
            
            for (Field campo : ReflectionUtils.recuperarCamposAnotadosCom(classe, Lookup.class)) {
                Object service = ServiceLocale.recuperarServico(campo.getType()); 
                
                campo.setAccessible(true);
                campo.set(instancia, service);
            }
            
            return instancia;
        } catch (SecurityException | IllegalAccessException | InstantiationException e) {
            throw new CMBReflectionException(e);
        }
    }

	@SuppressWarnings("unchecked")
	public static <T>T clonar(Class<T> classeDestino, Object origem) {
		Object objetoRetorno = instanciar(classeDestino);
		
		clonar(objetoRetorno, origem);
		
		return (T) objetoRetorno;
	}
	
	public static void clonar(Object destino, Object origem) {
		try {
	
			Class<? extends Object> classe = origem.getClass();
			while (!classe.equals(Object.class)) {
				
				for (Field campo : classe.getDeclaredFields()) {
					if(!Modifier.isStatic(campo.getModifiers())){
						Object valor = recuperarValorCampo(origem, campo.getName(), classe);

						Field campoDestino = classe.getDeclaredField(campo.getName());
						
						campoDestino.setAccessible(true);
						campoDestino.set(destino, valor); 
					}
				}
				
				classe = classe.getSuperclass();
			}
			
			
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new CMBReflectionException(e);
		}
	}

}

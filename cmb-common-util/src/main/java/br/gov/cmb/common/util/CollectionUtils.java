package br.gov.cmb.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class CollectionUtils {

    private CollectionUtils() {
    }
    
    public static boolean isNotEmpty(Collection<?> colecao) {
        return colecao != null && !colecao.isEmpty();
    }
    
    public static boolean isNullOrEmpty(Collection<?> colecao) {
        return colecao == null || colecao.isEmpty();
    }
    
    @SuppressWarnings("unchecked")
    public static <E> List<E> castList(List<?> list, Class<E> clazz){
     return (List<E>)(Object)list;
    }
    
    public static <E> List<E> toList(Set<E> set){
        if(set == null){
            return new ArrayList<E>();
        }
        
        return new ArrayList<E>((Collection<E>)set);
    }
}

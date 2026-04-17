package br.gov.cmb.common.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.gov.cmb.common.util.CollectionUtils;

public class CollectionUtilsIsNotEmptyTest {

    @Test
    public void testeComColecaoNulaDeveRetornarFalse() {
        // Act
        boolean resultado = CollectionUtils.isNotEmpty(null);

        // Arrange
        Assert.assertFalse(resultado);
    }

    @Test
    public void testeComColecaoVaziaDeveRetornarFalse() {
        // Act
        boolean resultado = CollectionUtils.isNotEmpty(Collections.EMPTY_LIST);

        // Arrange
        Assert.assertFalse(resultado);
    }

    @Test
    public void testeComColecaoVaziaDeveRetornarTrue() {
        // Arrange
        List<String> strings = Arrays.asList("Teste");

        // Act
        boolean resultado = CollectionUtils.isNotEmpty(strings);

        // Arrange
        Assert.assertTrue(resultado);
    }
}

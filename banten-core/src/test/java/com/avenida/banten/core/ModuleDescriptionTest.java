package com.avenida.banten.core;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class ModuleDescriptionTest {

  @Test public void test() {
    ModuleDescription moduleDescription;
    moduleDescription = new ModuleDescription(
        "test-name", "com.avenida.banten", "test", "../banten-core");

    assertThat(moduleDescription.getClasspath(), is("com.avenida.banten"));
    assertThat(moduleDescription.getName(), is("test-name"));
    assertThat(moduleDescription.getNamespace(), is("test"));
    assertThat(moduleDescription.getRelativePath(), is("../banten-core"));

  }

}

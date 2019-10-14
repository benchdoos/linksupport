package com.github.benchdoos.linksupport.links;

import com.github.benchdoos.linksupport.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class LinkTest extends AbstractTest {
    @Test
    public void getLinkByFile() {
        for (Link link : Link.values()) {
            final File file = new File(RESOURCES + File.separator + "test_link." + link.getExtension());
            Assertions.assertThat(file).isNotNull().exists();

            System.out.println("Checking " + link + " for file: " + file);

            final Link linkByFile = Link.getLinkForFile(file);
            Assertions.assertThat(linkByFile).isNotNull().isEqualTo(link);
            System.out.println("Checking successfully passed for: " + link);
        }
    }
}

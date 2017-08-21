package com.ilroberts;

import io.atlassian.fugue.Option;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class TransformerTest {

    private Transformer transformer;
    private String path = "./src/test/resources/test1.json";

    @Before
    public void init() {

        this.transformer = new Transformer();
    }

    @Test
    public void readJsonFromFileFail() {

        Option<String> out = transformer.readJsonFromFile("");
        assertThat(out.isEmpty()).isTrue();
    }

    @Test
    public void readJsonFromFileSucceed() {

        Option<String> out = transformer.readJsonFromFile(path);
        assertThat(out.isEmpty()).isFalse();
    }

    @Test
    public void copyJsonSuccess() {

        Option<String> in = transformer.readJsonFromFile(path);
        Option<String> out = transformer.copyJson(in.get());
        assertThat(in.get()).isEqualTo(out.get());
    }
}

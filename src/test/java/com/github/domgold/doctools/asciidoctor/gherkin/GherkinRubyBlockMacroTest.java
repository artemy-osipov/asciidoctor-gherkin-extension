package com.github.domgold.doctools.asciidoctor.gherkin;

import static org.asciidoctor.OptionsBuilder.options;
import static org.asciidoctor.AttributesBuilder.attributes;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.SafeMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GherkinRubyBlockMacroTest {

	private Asciidoctor asciidoctor;
	private Options options;

	@BeforeEach
	public void initAsciidoctor() throws IOException {
		asciidoctor = Asciidoctor.Factory.create();
		GherkinExtensionRegistry reg = new GherkinExtensionRegistry();
		reg.register(asciidoctor);
		File destinationDir = new File("build/test-output");
		if (!destinationDir.exists() && !destinationDir.mkdirs()) {
			throw new IOException("could not create test-output dir");
		}
		options = options().toDir(destinationDir).safe(SafeMode.UNSAFE)
				.get();
	}

	@Test
	void simpleTest() {
		asciidoctor.convertFile(new File("src/test/resources/simple_specs_example.adoc"), options);
	}

	@Test
	void complexTest() {
		asciidoctor.convertFile(new File(
				"src/test/resources/complex_specs_example.adoc"), options);
	}

	@Test
	void testWithUTF8FeatureBlockMacroAttributeANSI() {
		options.setAttributes(attributes().attribute("encoding", "iso-8859-1").get());
		asciidoctor.convertFile(new File("src/test/resources/block_macro_encoding_attribute_ANSI.adoc"), options);
	}

	@Test
	void testWithUTF8FeatureBlockMacroAttributeUTF8() {
		//UTF-8 is default
		//options.setAttributes(attributes().attribute("encoding", "UTF-8").get());
		asciidoctor.convertFile(new File("src/test/resources/block_macro_encoding_attribute_UTF8.adoc"), options);
	}

	@Test
	void testBlockMacroWithWrongEncodingAttributeFails() {
		options.setAttributes(attributes().attribute("encoding", "UTF-8").get());
		assertThrows(
				gherkin.lexer.LexingError.class,
				() -> asciidoctor.convertFile(new File("src/test/resources/block_macro_encoding_attribute_ANSI.adoc"), options)
		);
	}
}

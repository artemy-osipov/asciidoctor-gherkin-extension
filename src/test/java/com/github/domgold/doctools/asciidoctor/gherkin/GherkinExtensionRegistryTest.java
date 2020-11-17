package com.github.domgold.doctools.asciidoctor.gherkin;

import org.asciidoctor.Asciidoctor;
import org.junit.jupiter.api.Test;

class GherkinExtensionRegistryTest {

	@Test
	void test() {
		Asciidoctor asciidoctor = Asciidoctor.Factory.create();
		GherkinExtensionRegistry reg = new GherkinExtensionRegistry();
		reg.register(asciidoctor);
	}
}

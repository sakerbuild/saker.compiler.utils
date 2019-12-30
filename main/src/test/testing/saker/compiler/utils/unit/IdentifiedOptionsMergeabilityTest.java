package testing.saker.compiler.utils.unit;

import java.util.Map;

import saker.compiler.utils.api.options.CompilationIdentifier;
import saker.compiler.utils.api.options.CompilerUtils;
import testing.saker.SakerTest;
import testing.saker.SakerTestCase;

@SakerTest
public class IdentifiedOptionsMergeabilityTest extends SakerTestCase {
	@Override
	public void runTest(Map<String, String> parameters) throws Throwable {
		assertTrue(CompilerUtils.canMergeIdentifiers(cid("my-id"), cid("id")));
		assertTrue(CompilerUtils.canMergeIdentifiers(cid("my-id"), cid("my-id")));
		assertTrue(CompilerUtils.canMergeIdentifiers(cid("my-id"), null));
		assertFalse(CompilerUtils.canMergeIdentifiers(null, cid("my-id")));
		assertFalse(CompilerUtils.canMergeIdentifiers(cid("my-id"), cid("otherid")));
		assertFalse(CompilerUtils.canMergeIdentifiers(cid("my-id"), cid("my-otherid")));

		assertTrue(CompilerUtils.canMergeLanguages(null, null));
		assertTrue(CompilerUtils.canMergeLanguages("Java", null));
		assertTrue(CompilerUtils.canMergeLanguages("Java", "Java"));
		assertTrue(CompilerUtils.canMergeLanguages("java", "JAVA"));
		assertTrue(CompilerUtils.canMergeLanguages("JaVa", "JAvA"));

		assertFalse(CompilerUtils.canMergeLanguages("java", "c++"));
		assertFalse(CompilerUtils.canMergeLanguages(null, "Java"));
	}

	public static CompilationIdentifier cid(String id) {
		return CompilationIdentifier.valueOf(id);
	}
}

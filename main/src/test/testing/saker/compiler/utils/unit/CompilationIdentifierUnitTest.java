package testing.saker.compiler.utils.unit;

import java.util.Map;

import saker.compiler.utils.api.options.CompilationIdentifier;
import testing.saker.SakerTest;
import testing.saker.SakerTestCase;

@SakerTest
public class CompilationIdentifierUnitTest extends SakerTestCase {
	@Override
	public void runTest(Map<String, String> parameters) throws Throwable {
		assertEquals(cid("my-id"), cid("MY-iD"));
		assertEquals(cid("my-id"), CompilationIdentifier.concat(cid("my"), cid("id")));
		assertEquals(cid("my-id"), CompilationIdentifier.concat(cid("my-id"), cid("id")));
		assertEquals(cid("my-id-idx"), CompilationIdentifier.concat(cid("my-id"), cid("my-idx")));
		assertEquals(cid("my-id"), CompilationIdentifier.concat(cid("my-id"), null));
		assertEquals(cid("my-id"), CompilationIdentifier.concat(null, cid("my-id")));
		assertEquals(null, CompilationIdentifier.concat(null, null));

		assertEquals(cid("my-id").toString(), "my-id");
		assertEquals(cid("id-my").toString(), "id-my");

		assertEquals(cid("my-id"), cid("---my----id---"));
		assertEquals(cid("my-id"), cid("id-my"));

		assertException(IllegalArgumentException.class, () -> cid(""));
		assertException(NullPointerException.class, () -> cid(null));
		assertException(IllegalArgumentException.class, () -> cid("!!!-my-id"));
	}

	public static CompilationIdentifier cid(String id) {
		return CompilationIdentifier.valueOf(id);
	}

}

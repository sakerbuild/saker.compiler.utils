/*
 * Copyright (C) 2020 Bence Sipka
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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

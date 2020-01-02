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

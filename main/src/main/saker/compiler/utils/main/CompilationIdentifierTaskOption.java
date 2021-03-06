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
package saker.compiler.utils.main;

import java.util.Objects;

import saker.build.scripting.model.info.TypeInformationKind;
import saker.compiler.utils.api.CompilationIdentifier;
import saker.nest.scriptinfo.reflection.annot.NestInformation;
import saker.nest.scriptinfo.reflection.annot.NestTypeInformation;

@NestInformation("Represents an identifier for compilation related tasks.\n"
		+ "An identifier constists of dash separated parts of character sequences of a-z, A-Z, 0-9, _, ., (), [], @.\n"
		+ "The identifier parts are normalized to lower-case representation.\n"
		+ "In general, identifiers are used for two things:\n"
		+ "1. Determining the output location of the task. Different compilation tasks should have separate output locations "
		+ "in order to avoid overwriting each others data.\n"
		+ "2. Merging common compiler options with the input configuration. Compiler options can bear an identifier that "
		+ "specifies to which input configurations should they be merged into. If all parts in the options identifier are "
		+ "present in the compiler task identifier, then the options may be merged into the input configuration. This behaviour "
		+ "may be different based on task implementations.")
@NestTypeInformation(kind = TypeInformationKind.LITERAL)
public final class CompilationIdentifierTaskOption {
	private CompilationIdentifier identifier;

	public CompilationIdentifierTaskOption(CompilationIdentifier identifier) {
		this.identifier = identifier;
	}

	public CompilationIdentifier getIdentifier() {
		return identifier;
	}

	@Override
	public CompilationIdentifierTaskOption clone() {
		return this;
	}

	public static CompilationIdentifierTaskOption valueOf(CompilationIdentifier compilationid) {
		Objects.requireNonNull(compilationid, "compilation identifier");
		//clone in order to validate. CompilationIdentifier may be implemented by others in a non conforming way
		return new CompilationIdentifierTaskOption(CompilationIdentifier.valueOf(compilationid));
	}

	public static CompilationIdentifierTaskOption valueOf(String id) {
		return new CompilationIdentifierTaskOption(CompilationIdentifier.valueOf(id));
	}

	public static CompilationIdentifier getIdentifier(CompilationIdentifierTaskOption taskoption) {
		return taskoption == null ? null : taskoption.getIdentifier();
	}
}

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
package saker.compiler.utils.api.options;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import saker.build.thirdparty.saker.util.ImmutableUtils;
import saker.build.thirdparty.saker.util.ObjectUtils;
import saker.build.thirdparty.saker.util.StringUtils;
import saker.build.thirdparty.saker.util.io.SerialUtils;

final class CompilationIdentifierImpl implements CompilationIdentifier, Externalizable {
	private static final long serialVersionUID = 1L;

	private static final Pattern PATTERN_SPLIT = Pattern.compile("[-]+");
	private static final Pattern PATTERN_PART = Pattern.compile("[a-zA-Z0-9_.\\(\\)\\[\\]@]+");

	private Set<String> parts;

	/**
	 * For {@link Externalizable}.
	 */
	public CompilationIdentifierImpl() {
	}

	CompilationIdentifierImpl(Set<String> parts) {
		this.parts = ImmutableUtils.unmodifiableSet(parts);
	}

	@Override
	public Set<String> getParts() {
		return parts;
	}

	public static String toIdentifierPart(String s) {
		if (s == null) {
			return null;
		}
		return s.toLowerCase(Locale.ENGLISH);
	}

	public static CompilationIdentifier valueOf(String id) {
		Objects.requireNonNull(id, "id");
		if ("".equals(id)) {
			throw new IllegalArgumentException("Empty identifier.");
		}
		id = id.toLowerCase(Locale.ENGLISH);
		String[] split = PATTERN_SPLIT.split(id);
		Set<String> parts = new LinkedHashSet<>();
		for (String s : split) {
			if (ObjectUtils.isNullOrEmpty(s)) {
				continue;
			}
			if (!PATTERN_PART.matcher(s).matches()) {
				throw new IllegalArgumentException("Illegal identifier part: " + s);
			}
			parts.add(s);
		}
		if (parts.isEmpty()) {
			throw new IllegalArgumentException("No name parts specified in the compilation identifier: " + id);
		}
		return new CompilationIdentifierImpl(parts);
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		SerialUtils.writeExternalCollection(out, parts);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.parts = SerialUtils.readExternalImmutableLinkedHashSet(in);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getParts());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CompilationIdentifier)) {
			return false;
		}
		CompilationIdentifier other = (CompilationIdentifier) obj;
		if (!parts.equals(other.getParts())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return StringUtils.toStringJoin("-", parts);
	}

}

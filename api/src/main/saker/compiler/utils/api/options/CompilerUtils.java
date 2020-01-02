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

import java.util.Set;

import saker.build.thirdparty.saker.util.ObjectUtils;

/**
 * Utility class containing functions for working with compiler related use-cases.
 */
public class CompilerUtils {
	private CompilerUtils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if the identifiers should be a candidate for option merging.
	 * <p>
	 * The method will determine if the options associated with <code>optionsid</code> can be merged into the
	 * configuration associated with <code>targetid</code>.
	 * <p>
	 * The method examines the following, in this order: <br>
	 * If <code>optionsid</code> is <code>null</code>, <code>true</code> is returned. <br>
	 * If <code>targetid</code> is <code>null</code>, <code>false</code> is returned. <br>
	 * If <code>targetid</code> contains <b>all</b> name parts specified in <code>optionsid</code>, then
	 * <code>true</code> is returned. <br>
	 * Otherwise the result is <code>false</code>.
	 * 
	 * @param targetid
	 *            The identifier of the target configuration in which the options are merged.
	 * @param optionsid
	 *            The identifier of the option configuration that is being merged.
	 * @return <code>true</code> if the options can be merged based on the semantics specified by this method.
	 */
	public static boolean canMergeIdentifiers(CompilationIdentifier targetid, CompilationIdentifier optionsid) {
		if (optionsid == null) {
			return true;
		}
		if (targetid == null) {
			return false;
		}
		Set<String> targetparts = targetid.getParts();
		Set<String> otherparts = optionsid.getParts();
		if (targetparts.containsAll(otherparts)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the configurations can be merged for the argument languages.
	 * <p>
	 * The method will check if the options associated with <code>optionslang</code> can be merged into the
	 * configuration associated with <code>targetlang</code>.
	 * <p>
	 * This method can be used when you want to constrain the option merging by a language property.
	 * <p>
	 * The method examines the following, in this order: <br>
	 * If <code>optionslang</code> is <code>null</code> or empty, <code>true</code> is returned. <br>
	 * If <code>targetlang</code> is <code>null</code> or empty, <code>false</code> is returned. <br>
	 * If <code>targetlang</code> equals to <code>optionslang</code> in an ignore-case manner, then <code>true</code> is
	 * returned. <br>
	 * Otherwise the result is <code>false</code>.
	 * 
	 * @param targetlang
	 *            The language of the target configuration in which the options are merged.
	 * @param optionslang
	 *            The language of the option configuration that is being merged.
	 * @return <code>true</code> if the options can be merged based on the semantics specified by this method.
	 */
	public static boolean canMergeLanguages(String targetlang, String optionslang) {
		if (ObjectUtils.isNullOrEmpty(optionslang)) {
			//options are not language specialized, it can be merged based on the identifier
			return true;
		}
		if (ObjectUtils.isNullOrEmpty(targetlang)) {
			//target is null, while options have a language
			//cannot merge
			return false;
		}
		//both have languages, check them for ignore case equality
		return targetlang.equalsIgnoreCase(optionslang);
	}
}

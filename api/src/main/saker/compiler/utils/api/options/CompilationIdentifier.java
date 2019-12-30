package saker.compiler.utils.api.options;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import saker.build.thirdparty.saker.util.ObjectUtils;
import saker.build.thirdparty.saker.util.StringUtils;

/**
 * Interface for a compilation identifier that consists of dash separated lowercase name components.
 * <p>
 * A compilation identifier is used to uniquely identify a given compilation task in the build system. It is also used
 * to handle option merging for configurations.
 * <p>
 * The name components may consist of characters: <code>a-z</code>, <code>A-Z</code>, <code>0-9</code>, <code>_</code>,
 * <code>.</code>, <code>()</code>, <code>[]</code>, <code>@</code>. When a compilation identifier is constructed, the
 * name parts are normalized to lowercase representation.
 * <p>
 * Use the {@link #valueOf(String)} method to create a new instance.
 * <p>
 * This interface shouldn't be implemented by clients.
 */
public interface CompilationIdentifier {
	/**
	 * Gets the name parts of this compilation identifier.
	 * 
	 * @return An immutable set of name parts.
	 */
	public Set<String> getParts();

	/**
	 * Gets the hash code for the compilation identifier.
	 * <p>
	 * Defined as:
	 * 
	 * <pre>
	 * Objects.hashCode({@linkplain #getParts()});
	 * </pre>
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode();

	/**
	 * Checks if this compilation identifier equals to the argument.
	 * <p>
	 * Two compilation identifiers equal if they contain the same {@linkplain #getParts() name parts}.
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj);

	/**
	 * Converts the compilation identifier to string representation.
	 * <p>
	 * Defined as:
	 * 
	 * <pre>
	 * {@linkplain StringUtils#toStringJoin(CharSequence, Iterable) StringUtils.toStringJoin}("-", {@linkplain #getParts() parts});
	 * </pre>
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String toString();

	/**
	 * Creates a new compilation identifier by parsing the argument string.
	 * <p>
	 * The argument will be split by the dash (<code>'-'</code>) characters in it, and each component will be part of
	 * the resulting identifier. All empty and duplicate parts are omitted. Extraneous dashes are removed.
	 * 
	 * @param id
	 *            The input compilation identifier to parse.
	 * @return The created compilation identifier.
	 * @throws NullPointerException
	 *             If the argument is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             If the argument contains name parts with invalid characters, or no name parts are present.
	 */
	public static CompilationIdentifier valueOf(String id) throws NullPointerException, IllegalArgumentException {
		return CompilationIdentifierImpl.valueOf(id);
	}

	/**
	 * Clones the argument compilation identifier.
	 * <p>
	 * This method can be used to create a compilation identifier that is validated. As the
	 * {@link CompilationIdentifier} interface may be implemented by others, it may be necessary to validate it by
	 * creating a new instance. This can be used when you're working with compilation identifiers from external inputs.
	 * 
	 * @param clone
	 *            The compilation identifier to clone.
	 * @return The cloned identifier.
	 * @throws NullPointerException
	 *             If the argument is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             If the argument compilation identifier is semantically invalid.
	 */
	public static CompilationIdentifier valueOf(CompilationIdentifier clone)
			throws NullPointerException, IllegalArgumentException {
		Objects.requireNonNull(clone, "clone");
		Set<String> nparts = new LinkedHashSet<>();
		for (String p : Objects.requireNonNull(clone.getParts(), "clone identifier parts")) {
			if (ObjectUtils.isNullOrEmpty(p)) {
				//shouldn't happen, but other implementations can contain null parts
				continue;
			}
			nparts.add(CompilationIdentifierImpl.toIdentifierPart(p));
		}
		if (nparts.isEmpty()) {
			throw new IllegalArgumentException("No name parts specified in the compilation identifier: " + clone);
		}
		return new CompilationIdentifierImpl(nparts);
	}

	/**
	 * Concatenates two compilation identifiers.
	 * <p>
	 * The method will take the name parts of both arguments and create a new compilation identifier that contains the
	 * union of them.
	 * <p>
	 * If any of the argument is <code>null</code>, no concatenation is performed, and the other argument is returned.
	 * If both are <code>null</code>, the <code>null</code> is returned.
	 * 
	 * @param first
	 *            The first identifier.
	 * @param second
	 *            The second identifier.
	 * @return The concatenated identifier based on the arguments.
	 */
	public static CompilationIdentifier concat(CompilationIdentifier first, CompilationIdentifier second) {
		if (second == null) {
			return first;
		}
		if (first == null) {
			return second;
		}
		Set<String> nparts = new LinkedHashSet<>();
		for (String p : Objects.requireNonNull(first.getParts(), "first identifier parts")) {
			if (ObjectUtils.isNullOrEmpty(p)) {
				//shouldn't happen, but other implementations can contain null parts
				continue;
			}
			nparts.add(CompilationIdentifierImpl.toIdentifierPart(p));
		}
		for (String p : Objects.requireNonNull(second.getParts(), "second identifier parts")) {
			if (ObjectUtils.isNullOrEmpty(p)) {
				continue;
			}
			nparts.add(CompilationIdentifierImpl.toIdentifierPart(p));
		}
		if (nparts.isEmpty()) {
			throw new IllegalArgumentException(
					"No name parts specified for the compilation identifier: " + first + " and " + second);
		}
		return new CompilationIdentifierImpl(nparts);
	}
}

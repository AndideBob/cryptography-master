package de.andidebob.textbased.vigenere.kasiski.primefactor;

import java.util.*;
import java.util.stream.Collectors;

public class KasiskiCalculator {

    public Map<Integer, Integer> getCommonMultiples(Collection<Integer> numbers) {
        final Map<Integer, Integer> counters = new HashMap<>();
        for (Integer number : numbers) {
            Integer[] primeFactors = getPrimeFactors(number);
            Set<Integer> permutations = getPrimeFactorPermutations(primeFactors);
            for (Integer permutation : permutations) {
                incrementFor(permutation, counters);
            }
        }
        return counters;
    }

    private void incrementFor(Integer number, final Map<Integer, Integer> counters) {
        if (counters.containsKey(number)) {
            counters.put(number, counters.get(number) + 1);
        } else {
            counters.put(number, 1);
        }

    }

    private Set<Integer> getPrimeFactorPermutations(Integer[] factors) {
        final ArrayList<PrimeFactorPermutation> factorPermutations = new ArrayList<>();
        if (factors.length == 1) {
            return Set.of(factors[0]);
        }
        for (int i = 0; i < factors.length; i++) {
            // Add permutations with all previous permutations
            for (int l = 0; l < factorPermutations.size(); l++) {
                // Ignore if permutation already contains index
                if (factorPermutations.get(l).indices.contains(i)) {
                    continue;
                }
                // Factorize factor and add own index to indices
                int amount = factorPermutations.get(l).amount * factors[i];
                Set<Integer> newIndices = new HashSet<>(factorPermutations.get(l).indices);
                newIndices.add(i);
                addPermutationIfPossible(new PrimeFactorPermutation(amount, newIndices), factorPermutations);
            }
            // Add permutations with all following numbers
            PrimeFactorPermutation pfp = new PrimeFactorPermutation(factors[i], Set.of(i));
            addPermutationIfPossible(pfp, factorPermutations);
            for (int j = i + 1; j < factors.length; j++) {
                PrimeFactorPermutation pfp2 = new PrimeFactorPermutation(factors[i] * factors[j], Set.of(i, j));
                addPermutationIfPossible(pfp2, factorPermutations);
            }
        }
        return factorPermutations.stream().map(PrimeFactorPermutation::amount).collect(Collectors.toSet());
    }

    private void addPermutationIfPossible(PrimeFactorPermutation permutation, final List<PrimeFactorPermutation> list) {
        if (list.stream().anyMatch(permutationOld -> permutationOld.amount == permutation.amount)) {
            if (list.stream().noneMatch(permutationOld -> permutationOld.indices.containsAll(permutation.indices)
                    && permutation.indices.containsAll(permutationOld.indices))) {
                list.add(permutation);
            }
        } else {
            list.add(permutation);
        }
    }


    private record PrimeFactorPermutation(int amount, Set<Integer> indices) {
    }

    private Integer[] getPrimeFactors(int n) {
        List<Integer> factors = new ArrayList<>();

        // Handle divisions by 2
        while (n % 2 == 0) {
            factors.add(2);
            n /= 2;
        }

        // n must be odd at this point, so we can skip even numbers
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            // While i divides n, add i and divide n
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }

        // This condition is to check if n is a prime number greater than 2
        if (n > 2) {
            factors.add(n);
        }

        return factors.toArray(Integer[]::new);
    }
}

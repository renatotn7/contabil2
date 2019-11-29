package br.com.cvm.utils;

public class JaroWinklerStrategy extends JaroStrategy  {
	final double DEFAULT_SCALING_FACTOR = 0.1;  // This is the default scaling factor Winkler used.

    private double scalingFactor;
    
    /**
     * Constructs a new JaroWinklerStrategy instance.
     * @param scalingFactor The scaling factor between 0.00 and 0.25. If the scaling factor is greater than 0.25, the scaling factor is set to 0.25.
     */
    public JaroWinklerStrategy(double scalingFactor)
    {
        if (scalingFactor > 0.25)
        {
            scalingFactor = 0.25;    
        }
        this.scalingFactor = scalingFactor;
    }

    /**
     * Constructs a new JaroWinklerStrategy instance.
     */
    public JaroWinklerStrategy()
    {
        this.scalingFactor = DEFAULT_SCALING_FACTOR;
    }

    /**
	 * Calculates the similarity score of objects, where 0.0 implies absolutely no similarity
	 * and 1.0 implies absolute similarity.
	 * 
	 * @param first The first string to compare.
	 * @param second The second string to compare.
	 * @return A number between 0.0 and 1.0.
	 */
    public double score(String first, String second)
    {
        double jaro = super.score(first, second);

        int cl = commonPrefixLength(first, second);

        // The Jaro�Winkler distance uses a prefix scale which gives more favorable ratings
        // to strings that match from the beginning for a set prefix length.
        double winkler = jaro + (scalingFactor * cl * (1.0 - jaro));

        return winkler;

    }

    /**
     * Calculates the number of characters from the beginning of the strings that match exactly one-to-one, 
     * up to a maximum of four (4) characters.
     * @param first The first string.
     * @param second The second string.
     * @return A number between 0 and 4.
     */
    private int commonPrefixLength(String first, String second)
    {
        String shorter;
        String longer;

        // Determine which string is longer.
        if (first.length() > second.length())
        {
            longer = first.toLowerCase();
            shorter = second.toLowerCase();
        }
        else
        {
            longer = second.toLowerCase();
            shorter = first.toLowerCase();
        }

        int result = 0;

        // Iterate through the shorter string.
        for (int i = 0; i < shorter.length(); i++)
        {
            if (shorter.charAt(i) != longer.charAt(i))
            {
                break;
            }
            result++;
        }

        // Limit the result to 4.
        return result > 4? 4: result;
    }

	
}
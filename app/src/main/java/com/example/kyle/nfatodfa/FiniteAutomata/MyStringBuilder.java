package com.example.kyle.nfatodfa.FiniteAutomata;

/**
 * My custom StringBuilder
 * It's used in NFAConverter to aid in the conversion process.
 *
 * @author Kyle Williams
 * @since 1/4/16
 */
final class MyStringBuilder {
    StringBuilder stringBuilder = new StringBuilder();

    void append(String string){
        stringBuilder.append(string);
        stringBuilder.append(" ");
    }

    /**
     * Clears the contents of the current MyStringBuilder instance
     */
    void clear(){
        // Special thanks to anubhava for his answer on Stack Overflow regarding
        // whether it is better to reuse an existing StringBuilder or reallocate a new one.
        stringBuilder.setLength(0); // Set length of buffer to 0
        stringBuilder.trimToSize(); // Trim the underlying buffer
    }

    public String toString(){
        String string = stringBuilder.toString();
        return string.trim();
    }

    /**
     * Replaces the contents of the MyStringBuilder instance with "string"
     * @param string the string that will replace the string currently held by MyStringBuilder
     */
    void replaceWith(String string){
        stringBuilder.replace(0, stringBuilder.length()-1, string);
    }
}

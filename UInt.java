package project1CS3310;

import java.util.Arrays;

public class UInt
{
    private boolean[] bits;
    private int bitWidth;

    public UInt(int width, int value)
    {
        bitWidth = width;
        bits = new boolean[width];

        for(int i = width-1;i >= 0;i--)
        {
            bits[i] = !(value % 2 == 0);
            value = value >> 1;
        }
    }

    /**
     * <h1>UInt</h1>
     * A constructor used to make a copy of an existing instance of UInt, but with a wider bit width
     * If the new width is less than the old bitWidth, this method can at best create a copy with the most significant bits truncated
     * Note that to retain the correct value, the most-significant bits of the new instance should be padded with 0 (false)
     * </p>
     * @param width The new bit width for the copy
     * @param toCopy The UInt to be copied
     */
    public UInt(int width, UInt toCopy)
    {
        boolean[] newBits= new boolean[width];
        boolean[] toCopyBits = toCopy.getBits();
        int copyWidth = toCopy.getBitWidth();

        // Copying the bits from toCopy to newBits
        for (int i = 0; i < Math.min(width, copyWidth); i++) {
            newBits[width - 1 - i] = toCopyBits[copyWidth - 1 - i];
        }

        bits = newBits;
        bitWidth = width;
    }


    public UInt(int newWidth, boolean[] resultBits) {
		// TODO Auto-generated constructor stub
    	bitWidth = newWidth;
        bits = resultBits;
	}

	/**
     * <h1>getBitWidth</h1>
     * Returns the value of bitWidth
     * </p>
     * @return the int value bitWidth
     */
    public int getBitWidth()
    {
        return bitWidth;
    }
    
    public boolean[] getBits() {
		return bits;
    }
    public UInt trimLeadingZeros() {
        int leadingZeros = 0;
        while (leadingZeros < bitWidth && !bits[leadingZeros]) {
            leadingZeros++;
        }

        int newBitWidth = bitWidth - leadingZeros;
        if (newBitWidth == 0) {  // If all bits are zero
            return new UInt(1, 0);  // Return a UInt of width 1 with value 0
        }

        boolean[] newBits = new boolean[newBitWidth];
        System.arraycopy(bits, leadingZeros, newBits, 0, newBitWidth);

        return new UInt(newBitWidth, newBits);
    }

    /**
     * <h1>onesComplement</h1>
     * Replaces bits[] with the 1's complement inversion of bits[]
     */
    public UInt onesComplement() {
        boolean[] complementBits = new boolean[bitWidth];
        for (int i = 0; i < bitWidth; i++) {
            complementBits[i] = !bits[i];
        }
        return new UInt(bitWidth, complementBits);
    }

    /**
     * <h1>twosComplement</h1>
     * Replaces bits[] with the 2's complement inversion of bits[]
     */
    public UInt twosComplement() {
        // First, get the 1's complement
        UInt result = this.onesComplement();

        // Then, add 1 to the result
        boolean carry = true;
        for (int i = result.bitWidth - 1; i >= 0 && carry; i--) {
            if (result.bits[i]) {
                result.bits[i] = false;
            } else {
                result.bits[i] = true;
                carry = false;
            }
        }

        return result;
    }

    /**
     * <h1>bitwiseAnd</h1>
     * Accepts another UInt called value and performs a bitwise and on this and value, then returns the result
     * </p>
     * @param value A UInt
     * @return The result (this and value)
     */
    public UInt bitwiseAnd(UInt value) {
        int maxBitWidth = Math.max(this.bitWidth, value.getBitWidth());
        
        UInt extendedThis = new UInt(maxBitWidth, this);
        UInt extendedValue = new UInt(maxBitWidth, value);

        boolean[] resultBits = new boolean[maxBitWidth];

        for (int i = 0; i < maxBitWidth; i++) {
            resultBits[i] = extendedThis.bits[i] && extendedValue.bits[i];
        }

        return new UInt(maxBitWidth, resultBits);
    }


    /**
     * <h1>bitwiseOr</h1>
     * Accepts another UInt called value and performs a bitwise or on this and value, then returns the result
     * </p>
     * @param value A UInt
     * @return The result (this or value)
     */
    public UInt bitwiseOr(UInt value) {
        int maxBitWidth = Math.max(this.bitWidth, value.getBitWidth());
        
        UInt extendedThis = new UInt(maxBitWidth, this);
        UInt extendedValue = new UInt(maxBitWidth, value);

        boolean[] resultBits = new boolean[maxBitWidth];

        for (int i = 0; i < maxBitWidth; i++) {
            resultBits[i] = extendedThis.bits[i] || extendedValue.bits[i];
        }

        return new UInt(maxBitWidth, resultBits);
    }


    /**
     * <h1>bitwiseXor</h1>
     * Accepts another UInt called value and performs a bitwise xor on this and value, then returns the result
     * </p>
     * @param value A UInt
     * @return The result (this xor value)
     */
    public UInt bitwiseXor(UInt value)
    {
    	 // Determine the maximum bit width between the two UInts
        int maxBitWidth = Math.max(this.bitWidth, value.getBitWidth());

        // Use the constructor to ensure both operands have the maxBitWidth
        UInt extendedThis = new UInt(maxBitWidth, this);
        UInt extendedValue = new UInt(maxBitWidth, value);

        boolean[] resultBits = new boolean[maxBitWidth];

        // Perform the bitwise XOR operation
        for (int i = 0; i < maxBitWidth; i++) {
            resultBits[i] = extendedThis.bits[i] ^ extendedValue.bits[i];
            
        }
        
        System.out.println("Result after XOR: " + new UInt(maxBitWidth, resultBits));

        return new UInt(maxBitWidth, resultBits);
    }

    /**
     * <h1>add</h1>
     * Accepts another UInt called value and returns the sum of this UInt plus value
     * </p>
     * @param value A UInt to add
     * @return The sum (this plus value)
     */
    public UInt add(UInt value) {
        // Ensure both operands have the same bitWidth, which is the greater of the two +1 for possible carry.
        int newWidth = Math.max(this.bitWidth, value.getBitWidth()) + 1;
        UInt extendedThis = new UInt(newWidth, this);
        UInt extendedValue = new UInt(newWidth, value);

        boolean[] resultBits = new boolean[newWidth];
        boolean carry = false;

        for (int i = newWidth - 1; i >= 0; i--) {
            boolean bit1 = extendedThis.bits[i];
            boolean bit2 = extendedValue.bits[i];

            // XOR for sum, AND for carry
            resultBits[i] = bit1 ^ bit2 ^ carry;
            carry = (bit1 && bit2) || (bit1 && carry) || (bit2 && carry);
        }

        return new UInt(newWidth, resultBits).trimLeadingZeros();
    }


    /**
     * <h1>add</h1>
     * Static method, takes two UInt objects and returns the sum val1 times val2
     * </p>
     * @param val1 A UInt
     * @param val2 A UInt
     * @return The sum (val1 plus val2)
     */
    public static UInt add(UInt val1, UInt val2)
    {
    	int width1 = val1.getBitWidth();
        int width2 = val2.getBitWidth();
        int newWidth = Math.max(width1, width2) + 1; // +1 for possible carry

        boolean[] resultBits = new boolean[newWidth];
        boolean carry = false;

        for (int i = 0; i < newWidth; i++)
        {
            boolean bit1 = i < width1 ? val1.getBits()[width1 - 1 - i] : false;
            boolean bit2 = i < width2 ? val2.getBits()[width2 - 1 - i] : false;

            // XOR for sum, AND for carry
            resultBits[newWidth - 1 - i] = bit1 ^ bit2 ^ carry;
            carry = (bit1 && bit2) || (bit1 && carry) || (bit2 && carry);
        }

        return new UInt(newWidth, resultBits).trimLeadingZeros();
    }
    
    private UInt addWithoutTrimming(UInt val1) {
    	
    	// Ensure both operands have the same bitWidth, which is the greater of the two +1 for possible carry.
        int newWidth = Math.max(this.bitWidth, val1.getBitWidth())+1;
        UInt extendedThis = new UInt(newWidth, this);
        UInt extendedValue = new UInt(newWidth, val1);

        boolean[] resultBits = new boolean[newWidth];
        boolean carry = false;

        for (int i = newWidth - 1; i >= 0; i--) {
            boolean bit1 = extendedThis.bits[i];
            boolean bit2 = extendedValue.bits[i];

            // XOR for sum, AND for carry
            resultBits[i] = bit1 ^ bit2 ^ carry;
            carry = (bit1 && bit2) || (bit1 && carry) || (bit2 && carry);
        }
        
     // Check if the MSB is a carry bit and drop it if it is
        if (resultBits[0]) {
            boolean[] adjustedBits = new boolean[newWidth - 1];
            System.arraycopy(resultBits, 1, adjustedBits, 0, newWidth - 1);
            return new UInt(newWidth - 1, adjustedBits);
        }
        
        return new UInt(newWidth, resultBits);
    }

    /**
     * <h1>mul</h1>
     * Accepts another UInt called value and returns the product of this UInt times value
     * </p>
     * @param value A UInt multiplier
     * @return The product (this times value)
     */
    public UInt mul(UInt value)
    {
    	return UInt.mul(this, value).trimLeadingZeros();
    }
    
    /**
     * <h1>extendWidth</h1>
     * Extends the width of a given UInt and places its bits to the leftmost, filling the rest with zeros.
     * </p>
     * @param newWidth The new width for the UInt.
     * @param value The original UInt.
     * @return A new UInt with extended width.
     */
    public UInt extendWidth(int newWidth, UInt value) {
        boolean[] newBits = new boolean[newWidth];
        boolean[] oldBits = value.getBits();
       
        int oldWidth = value.getBitWidth();
      
        for (int i = 0; i < oldWidth; i++) {
        	newBits[i] = oldBits[i];
        }

        return new UInt(newWidth, newBits);
    }
    
    private static UInt constructP(int x, UInt r, int newWidth) {
        boolean[] bits = new boolean[newWidth];
        
        // Fill the most significant x bits with zeros (already false due to array initialization)
        
        // To the right of this, append the value of r
        boolean[] rBits = r.getBits();
        for (int i = x; i < newWidth - 1; i++) {
            bits[i] = rBits[i - x];
        }
        
        // Fill the least significant (rightmost) bit with a zero (already false due to array initialization)
        
        return new UInt(newWidth, bits);
    }
    
    private static UInt arithmeticRightShift(UInt value) {
        boolean[] bits = value.getBits();
        boolean[] shiftedBits = new boolean[bits.length];

        // The MSB remains the same
        shiftedBits[0] = bits[0];

        // Shift other bits
        for (int i = 1; i < bits.length; i++) {
            shiftedBits[i] = bits[i - 1];
        }

        return new UInt(bits.length, shiftedBits);
    }
    
    private static UInt dropLeastSignificantBit(UInt value) {
        boolean[] bits = value.getBits();
        boolean[] newBits = new boolean[bits.length - 1];

        // Copy all but the last bit
        for (int i = 0; i < bits.length - 1; i++) {
            newBits[i] = bits[i];
        }

        return new UInt(bits.length - 1, newBits);
    }

    /**
     * <h1>mul</h1>
     * Static method, takes two UInt objects and returns the product val1 times val2
     * </p>
     * @param m The UInt multiplicand
     * @param r The UInt multiplier
     * @return The product (val1 times val2)
     */
    public static UInt mul(UInt m, UInt r) {
        int x = m.getBitWidth();
        int y = r.getBitWidth();
        int newWidth = x + y + 1;

        UInt a = new UInt(newWidth, 0);
        a = a.extendWidth(newWidth, m);
        UInt s = new UInt(newWidth, 0);
        s = s.extendWidth(newWidth, m.twosComplement());
        UInt p = constructP(x, r, newWidth);
 
        for (int i = 0; i < y; i++) {
            // Step 2: Check the two least significant bits of P and adjust P accordingly
            boolean[] pBits = p.getBits();
            System.out.println("pbits is: " + Arrays.toString(pBits));
            boolean lastBit = pBits[newWidth - 1];
            boolean secondLastBit = pBits[newWidth - 2];
            System.out.println("lastbit is: " + lastBit);
            System.out.println("secondLastbit is: " + secondLastBit);

            if (lastBit && !secondLastBit) {
                // 01 case
                p = p.addWithoutTrimming(a); // Add A to P
                System.out.println("Added a. New p: " + p);
            } else if (!lastBit && secondLastBit) {
                // 10 case
                p = p.addWithoutTrimming(s); // Add S to P
                System.out.println("Added s. New p: " + p);
            }else {
            	System.out.println("No addition performed for iteration " + i);  // Debug line
            }
            // For 00 and 11, do nothing.
            System.out.println("P after operation: "+ p.toString());

            // Step 3: Arithmetically shift P to the right by one position
            p = arithmeticRightShift(p);
            
            System.out.println("p after arithmetic shift at iteration " + i + ": " + p.toString());  // Debug line
        }
       
        p = dropLeastSignificantBit(p).trimLeadingZeros();
        newWidth = p.getBitWidth();  // Update newWidth after trimming leading zeros
        
        return p;
    }

    /**
     * <h1>toString</h1>
     * Converts the boolean array bits to a binary String representation
     * Overrides the built-in toString() method in Java.lang.Object, thus is automatically called whenever an instance of UInt is used as a String in a function call
     * </p>
     * @return A String containing "0b" + the binary representation of bits
     */
    @Override
    public String toString()
    {
        String binary = "0b";
        for(int i = 0; i < bitWidth; i++) {
            binary += bits[i] ? "1" : "0";
        }
        return binary;
    }

    /**
     * <h1>toInt</h1>
     * Converts the binary representation bits into a (positive) int value
     * </p>
     * @return The int form of our boolean array
     */
    public int toInt()
    {
        int value = 0;
        for (int i = 0; i < bitWidth; i++)
        {
            if (bits[i])
            {
                value += Math.pow(2, bitWidth - 1 - i);
            }
        }
        return value;
    }


}


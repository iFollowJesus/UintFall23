# UintFall23
The UInt.java program defines a class UInt for representing unsigned integers with a specific bit width in Java. Here’s a detailed description of its functionality:
## Overview

    The UInt class encapsulates an unsigned integer value using a boolean array (bits) to represent its binary form.
    The bitWidth field denotes the size of the binary representation.

## Constructors

    UInt(int width, int value): Initializes a UInt object with a specified width and integer value. The integer value is converted into its binary representation fitting the given width.
    UInt(int width, UInt toCopy): Creates a new UInt instance by copying another, with the option to adjust the bit width, either truncating or padding with zeros as needed.
    UInt(int newWidth, boolean[] resultBits): Initializes a UInt with a specified bit width and a boolean array representing the binary form.

## Methods

    getBitWidth(): Returns the bit width of the UInt.
    getBits(): Returns the binary representation as a boolean array.
    trimLeadingZeros(): Returns a new UInt with leading zeros removed.
    onesComplement(): Computes the one's complement of the UInt.
    twosComplement(): Computes the two's complement of the UInt.
    bitwiseAnd(UInt value): Performs a bitwise AND operation with another UInt.
    bitwiseOr(UInt value): Performs a bitwise OR operation with another UInt.
    bitwiseXor(UInt value): Performs a bitwise XOR operation with another UInt.
    add(UInt value): Adds two UInt instances, adjusting bit width as necessary to accommodate the sum.
    mul(UInt value): Multiplies two UInt instances using an algorithm that considers bit width extensions and              arithmetic right shifts.
    toString(): Returns a string representation of the UInt in binary form, prefixed with 0b.
    toInt(): Converts the binary representation back to a positive integer.

## Usage

    This class is used for handling unsigned integers in a controlled binary context, useful for simulations, educational purposes, or environments where handling of raw binary data and custom bit widths is required.

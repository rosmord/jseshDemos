# multipleSignSources

**Currently, hieroglyphic font management in JSesh relies far too much on singletons**. I need to fix this.


A small demo about the way JSesh can be used with multiple hieroglyphic sign folders.

We define two folders, `signs1` and `signs2` and place glyphs there.

We want :

- to use signs in `signs2` and  `signs1`, preferably to standard JSesh signs ;
- to give signs in `signs1` precedence to those in `signs2`.

In practice, it means that :

- for G1, we will use the shape available in `signs1`
- for A1, we will use the shape available in `signs2`
- for other signs, we will use the shape available in `JSesh`.

**Currently, it doesn't work that way, because I use the same source for hieroglyphic fonts all over the place in JSesh code**. 

What is supposed to work right now : you *can* add source folders for glyphs, but they will be used **after** the standard ones.


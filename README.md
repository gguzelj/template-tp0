# template-tp0
![Build Status](https://travis-ci.org/gguzelj/template-tp0.svg?branch=master) 

# RegExGenerator
---

## Use

Regex generator it's pretty simple to use. Just call the generate method with a regex and receive the list of results.
Examples:

```java
//returns one string that matches the regex
regexGenerator.generate("[abc]");
```
or
```java
//Returns 2 different strings that matches the regex
regexGenerator.generate("[abc]", 2);
```

--- 
## Syntax

#### Any Character
Use '.' to generate a random [ASCII](https://en.wikipedia.org/wiki/ASCII) character. For example:
```java
regexGenerator.generate(".", 2); //Could return ["B", "p"]
```

#### Literal
Use '\' to escape any character. This forces the generator to generate that specific character. For example:
```java
regexGenerator.generate("\@", 2); //Should return ["@", "@"]
regexGenerator.generate("\/", 2); //Should return ["/", "/"]
```

#### Group
Generate a string with a reduce set of characters. 
A random character will be selected within the group. For example:
```java
regexGenerator.generate("[apC]", 2); //Should return ["a", "a"]
```
> The set of characters must be A-z, 0-9. Any other character (including quantifiers) must be escaped.
 

#### Quantifiers

##### ? (question mark):
This metacharacter denotes optionality, that means zero or one repetition. For example:
``` java
regexGenerator.generate("b\@?", 2); //Could return ["b@", "b"]
```

##### * (asterisk mark):
Used to indicate zero or more repetitions. For example:
``` java
regexGenerator.generate("b*", 2); //Could return ["", "bbbbb"]
```

##### + (plus mark):
Similar to *****, but forcing at least one repetition. For example:
``` java
regexGenerator.generate("b+", 2); //Could return ["b", "bb"]
```

## Examples
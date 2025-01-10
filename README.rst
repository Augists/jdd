JDD
===

This is a modified version of `jdd library <https://bitbucket.org/vahidi/jdd>`_.

Now in `NodeTable.java <src/jdd/bdd/NodeTable.java>`_ you can find a new variable::
    
    public static int mkCount = 0;

which counts the number of BDD nodes created. And in the `BDD.java <src/jdd/bdd/BDD.java>`_ you can find two new methods::

    public BitSet minAssignment(int bdd) {}

    public int toZero(int bdd) {}

where the first one returns the minimum assignment of the BDD. It is required by `JavaNDD <https://github.com/LZC-xjtu/network-decision-diagram>`_ (will be public later).
The second `toZero` method is used in bdd vector.

JDD Description
---------

.. image:: bdd.png
   :align: center


JDD is a decision diagram library written in pure java. It supports
`Binary Decision Diagrams <https://en.wikipedia.org/wiki/Binary_decision_diagram>`_ (BDD) and
`Zero-suppressed Decision Diagram <https://en.wikipedia.org/wiki/Zero-suppressed_decision_diagram>`_ (Z-BDD or just ZDD).

Binary Decision Diagrams (BDDs) are used in formal verification, CSP, optimisation and more.
To work with BDDs, you need a BDD library. JDD is my java implementation of a decision
diagram library inspired by BuDDy (a BDD package written in C).
It also includes support for Zero-suppressed BDDs.




Using JDD
---------

The `tutorial <TUTORIAL.rst>`_ is probably a good place to start.

The source code contains a number of examples under src/jdd/examples:

* BDDQueens, ZDDQueens and ZDDCSPQueens: N-Queens problem solved with BDDs, Z-BDDs and Z-BDD CSP operators
* Solitaire: The solitaire example from the BuDDy distribution
* Adder: Yet another example stolen from the BuDDy distribution
* Milner: Milner's scheduler, from BuDDy...


If you encounter any problems make sure to check out the `Frequently Asked Questions <FAQ.rst>`_.

Getting JDD
-----------

Get the code and compile it yourself::

    git clone https://bitbucket.org/vahidi/jdd.git

... or import it in Gradle::

    dependencies {
        compile 'org.bitbucket.vahidi.jdd:111'
    }

... or maven::

    <dependency>
        <groupId>org.bitbucket.vahidi</groupId>
        <artifactId>JDD</artifactId>
        <version>111</version>
    </dependency>



Building JDD
------------

We use Gradle to build JDD::

    git clone https://bitbucket.org/vahidi/jdd.git
    
    cd jdd
    ./gradlew build
    
    ls build/libs/



Or, as JDD has no external dependencies, you can just "javac" source files like this::

    mkdir -p build
    find src/ -name "*.java"  -exec javac -d build {} \+
    ls -l build/jdd



License
-------

JDD is free software under the zlib license. You may use it free of charge in research or even commercial projects.

For academic publications, feel free to use a bibtex entry similar to this for citation::

    @MISC{jdd,
        author = {Arash Vahidi},
        title = {JDD: a pure Java BDD and Z-BDD library},
        howpublished = "\url{https://bitbucket.org/vahidi/jdd}",
        year = 2003
    }

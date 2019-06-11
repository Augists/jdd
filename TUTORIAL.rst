JDD mini tutorial
*****************

(this tutorial is partially machine generated)


Getting started
---------------

To see if everything works correct, enter JDD directory and execute the following commands::

    $ ./gradlew build
    $ java -cp build/classes/java/main/ jdd.examples.BDDQueens 8
    BDD-Queen	Solutions=92.0	N=8	mem=0.77	time=46

What we just did was to run the BDD version of the "N Queens" example, which is included in the source code.

At this point, you should also see a freshly created JAR file under build/libs/.
Unless you plan to use maven/gradle, you should add this file to your project.

BDD tutorial
************

This part explains the basic BDD operations. It assumes however, that you are familiar with BDDs & co.

**Creating a BDD object**

The first thing to do, is to create a BDD object. This BDD object is your
base for BDD operations.  You may have several BDD objects in the same
applications, you may however not exchange information between these.

To create your a BDD object, you must specify the size of initial node table and cache. In this example we will use the values 10000 and 1000::

  BDD bdd = new BDD(1000,1000);


**Allocating variables**

Before you can use you BDD object any further, you must create some BDD variables. It is recommended that you create BDD variables only in the beginning of your work. BDD variables are in JDD represented by Java integer::

  int v1 = bdd.createVar();
  int v2 = bdd.createVar();
  int v3 = bdd.createVar();

Also, there are two special BDD variables that you do not need to allocate. These two are the boolean TRUE and FALSE. They are given the Java integer values 1 and 0.

**BDD operations**

BDD operations are carried out by simply calling the corresponding function in BDD::

  // note: this is actually wrong, see reference counting below
  int x = bdd.and(v1,v2);
  int y = bdd.xor(v1,v3);
  int z = bdd.not(v2);

You have now created three BDD trees.


**Reference counting**

Each BDD tree has a reference count. if this number is zero, your BDD tree may become removed by the internal garbage collector.The rule of thumb when working with BDDs is to reference your trees as soon as you get them, then de-reference them when you don need them anymore and they will be removed by the garbage collector at some future point::

  bdd.ref(x);
  bdd.ref(y);
  bdd.ref(z);

And when you are done with them, you just do this::

  bdd.deref(i_dont_need_this_bdd_anymore);


As garbage collection can happen at any time, it is important that BDDs are references as soon as possible.
Hence the previous example was incorrect and the right way to do it would be something like this::

  // note: this is the correct way to do BDD operations
  int x = bdd.ref( bdd.and(v1,v2) );
  int y = bdd.ref( bdd.xor(v1,v3) );
  int z = bdd.ref( bdd.not(v2) );


**Examining BDD trees**

It might be useful to actually see your BDDs. For that, JDD contains a set of functions. You can print the BDD as a set or a cube::

  bdd.printSet(y);
  bdd.printCubes(y);
  0-1
  1-0

   v3
   v1

However, the best way to visualize a BDD is to draw its graph.
To do this, JDD uses AT&T dot, which must be installed in your system and available from your shell prompt [i.e. in your $PATH]::

  bdd.printDot("x", x);
  bdd.printDot("y", y);
  bdd.printDot("v1", v1);



**Quantification**

You are allowed to apply exists and forall to a BDD tree.
The first thing you need to do is to create the cube of variables to be quantified. For example, if you would like to compute (forall x(v1v2) ), you may do this::

  int cube = jdd.ref( jdd.and(v1,v2) );

Then you can carry out the quantification::

  int x2 = jdd.ref( jdd.forall(x,cube) );

Note that we demonstrated the proper use of ref() here.

The exists() operators work similarly. Furthermore, there is a relProd operator that computes the relational product, i.e. exists C. X and Y = relProd(X,Y,C). This operations is very useful during image computation in model checking, for example.

There also exists a createCube function that you might find useful.

**Variable substitution**

It is sometimes desired to substitute variables in a tree. To do this, you first need a JDD permutation::

  int []p1 = new int[]{ v1 };
  int []p2 = new int[]{ v2 };
  Permutation perm1 = bdd.createPermutation(p1, p2);
  Permutation perm2 = bdd.createPermutation(p2, p1);

Now we have two permutation to change from v1 to v2 and vice versa. To use it, just call the replace() operations::

  int v12 = bdd.replace( v1, perm1);
  int v21 = bdd.replace( v2, perm2);

We have now swapped v1 and v2 in these tress...


**Next steps**

At this point you should have a basic understanding of how the library works.
Have a look at the examples included in JDD to learn how all this can be used to solve real problems.


Z-BDD Tutorial
**************

This tutorial demonstrates basic Z-BDD operations

Z-BDD Initialization
--------------------

There are several Z-BDD objects in JDD, they are however all created in a similar fashion to the BDD object. Consult BDD tutorial for more info.
The basic Z-BDD class is ZDD. It uses BDD-style initialization
::

  ZDD zdd = new ZDD(1000,100);

It contains all the basic operations, which are::

  base()
  empty()
  subset1()
  subset0()
  union()
  intersect()
  diff()
  change()

This sequence of code builds all the examples found in Minato original paper::

  ZDD zdd = new ZDD(1000,100);
  int v1 = zdd.createVar();
  int v2 = zdd.createVar();

  int a = zdd.empty();
  int b = zdd.base();
  int c = zdd.change(b, v1);
  int d = zdd.change(b, v2);
  int e = zdd.union(c,d);
  int f = zdd.union(b,e);
  int g = zdd.diff(f,c);


Note that in contrast to BDDs, Z-BDD variables (here v1 and v2) are just number and no Z-BDD trees. You can't do stuff like int a = zdd.union(v1,v2)!!!

As with BDDs, you can visualize Z-BDD trees by single calls in JDD::

  zdd.print(g);
  zdd.printSet(g);
  zdd.printCubes(g);
  7. v2: 1, 1

  { base, v2 }
  { 00, 10 }

But you will probably prefer the DOT printer printDot() ...



Additional Z-BDD operators
--------------------------

The ZDD class has some sub-classes with additional operators. These operators are used in more advanced applications. In some cases, the new operators outperform the basic Z-BDD operators, see for example the N Queens applet where a Z-BDD/CSP algorithms is included.

ZDD2
====

ZDD2 contains additional operations for unate cube set algebra. These operations are shown below
::

  void	showStats
  int	mul	( int, int )
  int	div	( int, int )
  int	mod	( int, int )

ZDDCSP
======

ZDDCSP adds extra ZDD operations for CSP problems. it is based on 'On the properties of combination set operations', by Okuno, Minato and Isozaki.
::

  int	restrict	( int, int )
  void	showStats
  int	exclude_slow	( int, int )
  int	exclude	( int, int )


ZDDGraph
========

ZDDGraph is intended to [in near future] include common ZDD operations used in graph algorithms, as explained in Coudert's paper.
::

  int	allEdge	( int, int )
  int	allEdge
  int	noSubset	( int, int )
  int	noSupset	( int, int )
  int	maxSet	( int )


The AT&T DOT utility
********************

Graphviz from AT&T is a public domain package for drawing graphs from a textual description.
Since DOT is used by JDD to visualize all types of graph, we felt it would be important to give some information about the dot support in JDD.

In JDD, each package that uses DOT, has a class named XXXPrinter, e.g. ZDDPrinter

These classes produce, among others, graphs in DOT format which is then converted to a graphic file by starting the DOT utility from a class in JDD called Dot::

  public static final int jdd.util.Dot.TYPE_EPS
  public static final int jdd.util.Dot.TYPE_PNG
  public static final int jdd.util.Dot.TYPE_DOT
  public static final int jdd.util.Dot.TYPE_FIG
  public static final int jdd.util.Dot.TYPE_GIF
  public static final int jdd.util.Dot.TYPE_JPG
  boolean	scaleable
  void	setType	( int )
  void	showDot	( java.lang.String )
  void	showString	( java.lang.String, java.lang.String )
  void	setExecuteDot	( boolean )
  void	setRemoveDotFile	( boolean )

It is important to know that a call to Dot.showDot(file) will remove you textual description file (here 'file') from your system! You can turn this off by the call to Dot.setRemoveDotFile(false);

You can choose from a set of possible file formats, such as EPS and JPEG. For example, Dot.setType( Dot.TYPE_TYPE_PNG) will set the output format to PNG. Furthermore, Dot.scaleable() returns true if the requested format is scalable (such as EPS).


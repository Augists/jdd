
package jdd.sat.bdd;

import jdd.bdd.*;
import jdd.sat.*;
import jdd.util.*;

import java.io.*;


public class BDDSolver  implements Solver {
	protected CNF cnf;
	protected boolean verbose;
	protected int clause_count, bdd_all;
	protected BDD jdd;


	public BDDSolver(boolean verbose) {
		this.verbose = verbose;
		cleanup(); // :)
	}

	public void cleanup() {	cnf = null;	jdd = null; }
	public void setFormula(CNF cnf) {	this.cnf = cnf;	}

	public int [] solve() {
		int nodes = Math.min(150000, 10000 + 2 * cnf.num_lits * cnf.curr );
		int cache = 4000;

		jdd = new BDD(nodes, cache);

		Options.verbose = verbose;
		long time = System.currentTimeMillis();

		// alloc BDD vars
		Var [] vars = cnf.vars;
		for(int i = 0; i < cnf.num_lits; i++) {
                        final int v = jdd.createVar();
                        vars[i].var.bdd = v;
                        vars[i].negvar.bdd = jdd.ref( jdd.not(v));
		}
		clause_count = 0;

		// build the formula line by line:
		bdd_all = 1;
		for(int cs = 0; cs < cnf.curr; cs++) {
			Clause c = nextClause();
			int bdd = clauseBDD(c);
			int tmp = jdd.ref( jdd.and( bdd_all, bdd) );
			jdd.deref( bdd_all);
			jdd.deref(bdd);
			bdd_all = tmp;

			if(bdd_all == 0) break; // no idea to continue
		}


		time = System.currentTimeMillis() - time;

		// ------------------- show the result:
		int [] tmp,ret = null;
		if(bdd_all == 0) JDDConsole.out.println("UNSAT/" + time + "ms");
		else {
			double count = jdd.satCount(bdd_all);
                        JDDConsole.out.println("SAT(" + count + " solutions)/"+time + "ms");
			tmp = jdd.oneSat(bdd_all, null);

			// convert to the original literal ordering
			ret = new int[tmp.length];
			for(int i = 0; i < ret.length; i++) {
				ret[i] = tmp[ cnf.vars[i].offset  ];
			}
		}

		jdd.deref(bdd_all);
		if(verbose) jdd.showStats();
		jdd.cleanup();
		jdd = null;



		return ret;
	}

	protected int getBDD(Lit l) {
		return l.bdd;
	}

	protected Clause nextClause() {
		return cnf.clauses[clause_count++];
	}


	protected int clauseBDD(Clause c) {
		int bdd = 0;
		for(int ls = 0; ls < c.curr; ls ++) {
			int  tmp = jdd.ref( jdd.or(bdd, getBDD(c.lits[ls])  ) );
			jdd.deref(bdd);
			bdd = tmp;
		}
		return bdd;
	}


	// ----------------------------------------------------
	/** I have no idea what this function does ;) */
	public static void main(String args[]) {
		if(args.length == 0) System.err.println("Need DIMACS file as argument");
		else for(int i = 0; i < args.length;i++) {
			try {
				System.out.print("Solving " + args[i] + "\t\t");
				DimacsReader dr = new DimacsReader(args[i], true);
				BDDSolver solver = new BDDSolver(false);
				solver.setFormula(dr.getFormula() );
				dr = null;
				solver.solve();
				solver.cleanup();
			} catch(IOException exx) { exx.printStackTrace(); }
		}
	}
}

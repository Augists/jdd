
package jdd.zdd;


import jdd.util.*;
import jdd.util.math.*;

import jdd.bdd.*;

/**
 * ZDD with graph algorithms from Coudert's paper.
 *
 * This class is not yet fully implemneted, due to lack of time :(
 *
 */

public class ZDDGraph  extends ZDD  {
    protected  static final int CACHE_NOSUBSET = 0, CACHE_NOSUPSET = 1;
	protected OptimizedCache graph_cache;
    
	public ZDDGraph(int nodesize, int cachesize) {
		super(nodesize, cachesize);
        graph_cache = new OptimizedCache("graph", cachesize / Configuration.zddGraphCacheDiv , 3, 2);
	}
    
    // ---------------------------------------------------------------
	public void cleanup() {
		super.cleanup();
		graph_cache = null;
	}
	// ---------------------------------------------------------------


	protected void post_removal_callbak() {
		super.post_removal_callbak();
		graph_cache.free_or_grow(this);
	}

	// ---------------------------------------------------------------

	/**
	 * all pairs, that is, all possible edges in a fully connected graph.
	 * if V = { x_from, ..., x_to} then  allEdge(V) = { (v1,v2) | v1,v2 \in V. v1 != v2 }
	 */
	public int allEdge() { return allEdge(0, num_vars-1); }
	public int allEdge(int from, int to) {
		if(to < from) return 0;

		int left = 0, right = mk(from, 0,1);
		work_stack[work_stack_tos++]  = left; // place holders
		work_stack[work_stack_tos++]  = right;
		for(int i = from+1; i < to; i++) {
			int tmp1 = work_stack[work_stack_tos++]  = mk(i,left, right);
			int tmp2 = work_stack[work_stack_tos++]  = mk(i,right, 1);
			work_stack_tos -= 4;
			left = work_stack[work_stack_tos++]  = tmp1;
			right = work_stack[work_stack_tos++]  = tmp2;
		}
		int ret = mk(to, left, right);
		work_stack_tos -= 2;
		return ret;
	}

	// ------------------------------------------------------------------------    
    
    /** 
     * 
     * noSubset(F, C) = {f \in F | \lnot \exist c \in C. f \subseteq c } 
     * 
     */
    
    public final int noSubset(int F, int C) {        
        if(F == C || F == 1 || F == 0) return 0;
        if(C == 0) return F;
        if(C == 1) return diff(F, 1);
                
        if(graph_cache.lookup(F, C, CACHE_NOSUBSET)) return graph_cache.answer;
		int hash = graph_cache.hash_value;

        int ret;
        int fvar = getVar(F);
        int cvar = getVar(C);
        if(fvar > cvar) {
            int tmp1 = work_stack[work_stack_tos++] = noSubset(getLow(F), C);
            ret = mk (fvar, tmp1, getHigh(F));
            work_stack_tos--;
        } else if(fvar < cvar) {
            int tmp1 = work_stack[work_stack_tos++] = noSubset(F, getLow(C));
            int tmp2 = work_stack[work_stack_tos++] = noSubset(F, getHigh(C));
            ret = intersect( tmp1, tmp2);
            work_stack_tos -= 2;
        } else {
            int tmp1 = work_stack[work_stack_tos++] = noSubset(getLow(F), getLow(C));
            int tmp2 = work_stack[work_stack_tos++] = noSubset(getLow(F), getHigh(C));
            
            tmp1 = intersect( tmp1, tmp2);
            work_stack_tos -= 2; 
            work_stack[work_stack_tos++] = tmp1;
            
            tmp2 = work_stack[work_stack_tos++] = noSubset(getHigh(F), getHigh(C));
            ret = mk( fvar, tmp1, tmp2);
            work_stack_tos -= 2;
        }
        
        graph_cache.insert(hash, F, C, CACHE_NOSUBSET, ret);
        
        return ret;
    }
    
    
    /**
	 * noSupset is used to compute exclude.
	 *
	 * noSupset(F, C) = {f \in F | \lnot \exist c \in C. c \subseteq f }
     */

    public int noSupset(int F, int C)  {
		if( emptyIn(C)) return 0;
		return noSupset_rec(F, C);
	}


    private final int noSupset_rec(int F, int C) {
        
		if(F == 0 || C == 1 || F == C) return 0;
        if(F == 1 || C == 0) return F;         
        
		if(graph_cache.lookup(F, C, CACHE_NOSUPSET)) return graph_cache.answer;
		int hash = graph_cache.hash_value;

		int ret;
		int fvar = getVar(F);
		int cvar = getVar(C);

		if (fvar < cvar) {
			ret = noSupset_rec(F, getLow(C));
		} else if (fvar > cvar) {
			int tmp1 = work_stack[work_stack_tos++] = noSupset_rec(getHigh(F), C);
			int tmp2 = work_stack[work_stack_tos++] = noSupset_rec(getLow(F) , C);
			ret = mk(fvar, tmp2, tmp1);
			work_stack_tos -= 2;
		} else {
            
            int tmp1, tmp2;
            int C1 = getHigh(C);
            
            if( emptyIn(C1)) { 
                // special case, beause  noSupset( getHigh(F), C1) = 0
                work_stack[work_stack_tos++] = tmp1 = 0;
            } else {
                tmp1 = work_stack[work_stack_tos++] = noSupset_rec( getHigh(F), getLow(C));
                tmp2 = work_stack[work_stack_tos++] = noSupset_rec( getHigh(F), C1);                
                tmp1 = intersect(tmp1, tmp2);
                work_stack_tos -= 2;
                work_stack[work_stack_tos++] = tmp1;
            }


			tmp2 = work_stack[work_stack_tos++] = noSupset_rec( getLow(F), getLow(C));
			ret = mk(fvar, tmp2, tmp1);
			work_stack_tos -= 2;
		}

		graph_cache.insert(hash, F, C, CACHE_NOSUPSET, ret);
		return ret;
    }
     
  

	// ------------------------------------------------------------------------

	/**
	 * MaxSet(X) = { x \in X | \forall x' \in X. x  \subseteq x' ==> x = x' }
	 * that is:
	 * MaxSet(X) = { x \in X | \lnot \exist x' \in X - x. x  \subseteq x' }
	 */
	 public int maxSet(int X) {
		if(X < 2) return X;
		int T0 = work_stack[work_stack_tos++] = maxSet( getLow(X)) ;
		int T1 = work_stack[work_stack_tos++] = maxSet( getHigh(X));
		int T2 = work_stack[work_stack_tos++] = noSubset(T0, T1);

		T0 = mk( getVar(X), T2, T1);
		work_stack_tos -= 3;
		return T0;
	 }
	 // ------------------------------------------------------------------------
	 /**
	  *
	  * MaxDot(X,Y) = { x \cup y | x \in x, y \in y }.
	  *
	  * <b>XXX: this function is still under development! DO NOT USE</b>
	  */
	 public int MaxDot(int X, int Y) {
		if(X  < 2) return X;
		if(Y  < 2) return Y;

		int v = Math.max( getVar(X), getVar(Y) );
		int Xh = work_stack[work_stack_tos++]  = subset1( X, v);
		int Yh = work_stack[work_stack_tos++]  = subset1( Y, v);
		int Xl = work_stack[work_stack_tos++]  = subset0( X, v);
		int Yl = work_stack[work_stack_tos++]  = subset0( Y, v);

		return 0; // TODO
	 }
}


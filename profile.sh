#!/bin/sh
#
# script for quickl running some big examples...
#

if [ $# -eq 0 ] ; then
    echo "Valid targets are: time1, time2, ztime1, profile, sat, trace"
    exit 20
fi


for arg in "$@"
do
    case $arg in
        "profile" )
            echo generate some profiling info
            java -cp build -Xmx512M -Xms256M -Xprof jdd.examples.BDDQueens 12 > build/queens12.prof
            java -cp build -Xmx512M -Xms256M -Xprof jdd.examples.Adder 1024 > build/adder1024.prof
            java -cp build -Xmx512M -Xms256M -Xprof jdd.examples.Milner 76 > build/milner76.prof
        ;;

        "time1" )
            echo timinng queens	
            java -cp build -Xmx256M -Xms128M jdd.examples.BDDQueens 7
            java -cp build -Xmx256M -Xms128M jdd.examples.BDDQueens 7	
            java -cp build -Xmx256M -Xms128M jdd.examples.BDDQueens 8
            java -cp build -Xmx256M -Xms128M jdd.examples.BDDQueens 8
            java -cp build -Xmx256M -Xms128M jdd.examples.BDDQueens 9
            java -cp build -Xmx256M -Xms128M jdd.examples.BDDQueens 9
            java -cp build -Xmx256M -Xms128M jdd.examples.BDDQueens 10
            java -cp build -Xmx256M -Xms128M jdd.examples.BDDQueens 10
            java -cp build -Xmx256M -Xms128M jdd.examples.BDDQueens 11
            java -cp build -Xmx256M -Xms128M jdd.examples.BDDQueens 11
            java -cp build -Xmx256M -Xms128M jdd.examples.BDDQueens 12
            java -cp build -Xmx256M -Xms128M jdd.examples.BDDQueens 12
            # java -Xmx256M -Xms128M jdd.examples.BDDQueens 13
            ;;        

        "time2" )
            echo n-bit adder
            java -cp build -Xmx512M -Xms128M jdd.examples.Adder 16
            java -cp build -Xmx512M -Xms128M jdd.examples.Adder 32
            java -cp build -Xmx512M -Xms128M jdd.examples.Adder 64
            java -cp build -Xmx512M -Xms128M jdd.examples.Adder 128
            java -cp build -Xmx512M -Xms128M jdd.examples.Adder 256
            java -cp build -Xmx512M -Xms128M jdd.examples.Adder 512
            java -cp build -Xmx512M -Xms128M jdd.examples.Adder 1024

            echo milner stuff
            java -cp build -Xmx512M -Xms128M jdd.examples.Milner 16
            java -cp build -Xmx512M -Xms128M jdd.examples.Milner 32
            java -cp build -Xmx512M -Xms128M jdd.examples.Milner 48
            java -cp build -Xmx512M -Xms128M jdd.examples.Milner 56
            java -cp build -Xmx512M -Xms128M jdd.examples.Milner 64
            java -cp build -Xmx512M -Xms128M jdd.examples.Milner 72

            # TODO: this one runes out of memory, probably a bug somewhere :(
            # java -Xmx512M -Xms128M -cp build jdd.examples.Solitaire        
        ;;
        
        "ztime1" )
            echo Timing for ZDD queens
            echo Plain ZDD
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 7
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 7	
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 8
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 8
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 9
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 9
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 10
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 10
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 11
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 11
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 12
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 12
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 13
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDQueens 13

            echo CSP-ZDD
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 7
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 7	
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 8
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 8
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 9
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 9
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 10
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 10
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 11
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 11
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 12
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 12
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 13
            java -cp build -Xmx512M -Xms256M jdd.examples.ZDDCSPQueens 13
        ;;
                
        "trace" )        
            echo run the traces
            java -cp build -Xmx512M -Xms128M jdd.bdd.debug.BDDTraceSuite data/yangs_traces.zip 10240 > build/jdd_yangs_traces.txt
            java -cp build -Xmx512M -Xms128M jdd.bdd.debug.BDDTraceSuite data/iscas_c6288.zip 500000 > build/jdd_ISCAS85_traces.txt
            java -cp build -Xmx512M -Xms128M jdd.bdd.debug.BDDTraceSuite data/iscas_rest.zip 100000 >> build/jdd_ISCAS85_traces.txt
            java -cp build -Xmx512M -Xms128M jdd.bdd.debug.BDDTraceSuite data/velev_sss.zip 200000 > build/jdd_sss_traces.txt
            ;;

        "sat" )
#            FILES="dimacs/8xQueens.cnf.gz dimacs/aim-100-1_6-no-2.cnf.gz dimacs/aim-100-6_0-yes1-2.cnf.gz dimacs/aim-200-2_0-yes1-2.cnf.gz dimacs/aim-200-3_4-yes1-3.cnf.gz dimacs/aim-50-1_6-no-2.cnf.gz dimacs/aim-50-2_0-yes1-2.cnf.gz dimacs/aim-50-3_4-yes1-4.cnf.gz dimacs/dubois22.cnf.gz dimacs/par16-4.cnf.gz"
            FILES="data/dimacs100a.cnf  data/dimacs200.cnf  data/dimacs50b.cnf  data/Q8.cnf data/dimacs100b.cnf  data/dimacs50a.cnf  data/dimcs100.cnf"
            SOLVERS="jdd.sat.bdd.BDDSolver jdd.sat.bdd.BDDSolver2 jdd.sat.dpll.DPLLSolver_MOMS jdd.sat.dpll.DPLLSolver jdd.sat.gsat.GSATSolver jdd.sat.gsat.GSAT2Solver jdd.sat.gsat.WalkSATSKCSolver jdd.sat.gsat.WalkSATSolver"            
            for SOLVER in $SOLVERS
            do
                echo " *** Using $SOLVER *** "
                for FILE in $FILES
                do
                    time java -cp  build -Xmx512M -Xms128M $SOLVER $FILE
                done
            done
        ;;

        *)
            echo "Unkown target"
            exit 20
            ;;
   esac
done


#!/bin/sh
#
# script for quickl running some big examples...
#

if [ $# -eq 0 ] ; then
    echo "Valid targets are: time1, time2, ztime1, profile, trace"
    exit 20
fi

export J="java -cp build/classes -Xmx512M -Xms256M"

for arg in "$@"
do
    case $arg in
        "profile" )
            echo generate some profiling info
            $J -Xprof jdd.examples.BDDQueens 12 > build/queens12.prof
            $J -Xprof jdd.examples.Adder 1024 > build/adder1024.prof
            $J -Xprof jdd.examples.Milner 76 > build/milner76.prof
        ;;

        "time1" )
            echo timinng queens
            $J jdd.examples.BDDQueens 7
            $J jdd.examples.BDDQueens 7
            $J jdd.examples.BDDQueens 8
            $J jdd.examples.BDDQueens 8
            $J jdd.examples.BDDQueens 9
            $J jdd.examples.BDDQueens 9
            $J jdd.examples.BDDQueens 10
            $J jdd.examples.BDDQueens 10
            $J jdd.examples.BDDQueens 11
            $J jdd.examples.BDDQueens 11
            $J jdd.examples.BDDQueens 12
            $J jdd.examples.BDDQueens 12
            # java -Xmx256M -Xms128M jdd.examples.BDDQueens 13
            ;;

        "time2" )
            echo n-bit adder
            $J jdd.examples.Adder 16
            $J jdd.examples.Adder 32
            $J jdd.examples.Adder 64
            $J jdd.examples.Adder 128
            $J jdd.examples.Adder 256
            $J jdd.examples.Adder 512
            $J jdd.examples.Adder 1024

            echo milner stuff
            $J jdd.examples.Milner 16
            $J jdd.examples.Milner 32
            $J jdd.examples.Milner 48
            $J jdd.examples.Milner 56
            $J jdd.examples.Milner 64
            $J jdd.examples.Milner 72

            # TODO: this one runes out of memory, probably a bug somewhere :(
            # java -Xmx512M -Xms128M -cp build jdd.examples.Solitaire
        ;;

        "ztime1" )
            echo Timing for ZDD queens
            echo Plain ZDD
            $J jdd.examples.ZDDQueens 7
            $J jdd.examples.ZDDQueens 7
            $J jdd.examples.ZDDQueens 8
            $J jdd.examples.ZDDQueens 8
            $J jdd.examples.ZDDQueens 9
            $J jdd.examples.ZDDQueens 9
            $J jdd.examples.ZDDQueens 10
            $J jdd.examples.ZDDQueens 10
            $J jdd.examples.ZDDQueens 11
            $J jdd.examples.ZDDQueens 11
            $J jdd.examples.ZDDQueens 12
            $J jdd.examples.ZDDQueens 12
            $J jdd.examples.ZDDQueens 13
            $J jdd.examples.ZDDQueens 13

            echo CSP-ZDD
            $J jdd.examples.ZDDCSPQueens 7
            $J jdd.examples.ZDDCSPQueens 7
            $J jdd.examples.ZDDCSPQueens 8
            $J jdd.examples.ZDDCSPQueens 8
            $J jdd.examples.ZDDCSPQueens 9
            $J jdd.examples.ZDDCSPQueens 9
            $J jdd.examples.ZDDCSPQueens 10
            $J jdd.examples.ZDDCSPQueens 10
            $J jdd.examples.ZDDCSPQueens 11
            $J jdd.examples.ZDDCSPQueens 11
            $J jdd.examples.ZDDCSPQueens 12
            $J jdd.examples.ZDDCSPQueens 12
            $J jdd.examples.ZDDCSPQueens 13
            $J jdd.examples.ZDDCSPQueens 13
        ;;

        "trace" )
            echo run the traces
            $J jdd.bdd.debug.BDDTraceSuite data/yangs_traces.zip 10240 > build/jdd_yangs_traces.txt
            $J jdd.bdd.debug.BDDTraceSuite data/iscas_c6288.zip 500000 > build/jdd_ISCAS85_traces.txt
            $J jdd.bdd.debug.BDDTraceSuite data/iscas_rest.zip 100000 >> build/jdd_ISCAS85_traces.txt
            $J jdd.bdd.debug.BDDTraceSuite data/velev_sss.zip 200000 > build/jdd_sss_traces.txt
            ;;


        *)
            echo "Unkown target"
            exit 20
            ;;
   esac
done

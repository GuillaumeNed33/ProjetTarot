#!/bin/bash

 if [ $# -ne 1 ]
 then
   echo "Usage: './resizePictures.sh taille' avec la taille sous la forme largeurxhauteur (640x480)."
   exit -1
 fi

 rep=resized-$1

 if [ -d "$rep" ]; then
         echo "Repertoire $rep existe. Je ne le crée pas."
 else
         echo "Repertoire $rep n'existe pas. Je le crée."
         mkdir $rep
 fi

 if [ 'ls *JPG' ]; then
         for i in *.JPG
         do
                 let "n += 1"
         done

         echo "$n fichiers *.JPG existent. Je les converti."

         for i in *.JPG
         do
                 if [ -e $rep/$i ]; then
                         echo "Le fichier $i existe deja dans le repertoire $rep."
                 else
                         taille=`identify  $i | awk '{print $3}'`
                         echo "Le fichier $i($taille) sera converti en $1 et enregistre dans le repertoire $rep."
                         convert -geometry $1 $i $rep/$i
                 fi
         done
 else
         echo "Aucun fichiers *.JPG n'existent. Je ne fait rien."
 fi

 if [ 'ls *jpg' ]; then
         for i in *.jpg
         do
                 let "n += 1"
         done

         echo "$n fichiers *.jpg existent. Je les converti."

         for i in *.jpg
         do
                 if [ -e $rep/$i ]; then
                         echo "Le fichier $i existe deja dans le repertoire $rep."
                 else
                         taille=`identify  $i | awk '{print $3}'`
                         echo "Le fichier $i($taille) sera converti en $1 et enregistre dans le repertoire $rep."
                         convert -geometry $1 $i $rep/$i
                 fi
         done
 else
         echo "Aucun fichiers *.jpg n'existent. Je ne fait rien."
 fi

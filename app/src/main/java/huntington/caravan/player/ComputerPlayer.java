package huntington.caravan.player;
import huntington.caravan.R;
import huntington.caravan.deck.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import huntington.caravan.deck.Card;

/**
 * Created by Bradley on 11/9/2015.
 */
public class ComputerPlayer {
    public boolean aisequence = true;
    public static Random r = new Random();
    static int onetoseven = 0;
    static int onetothree = 0;
    static String s;
    private static List<List<Card>> oppCaravans = new ArrayList();
    private static List<List<Card>> oppCaravanFaceList = new ArrayList();
    private static List<Integer> oppCaravansRanks = new ArrayList();
    private static List<Integer> userCaravansRanks = new ArrayList();
    static boolean played = false;

    int caravan1 = 0;

    public static void makePlay(List<Card> computerhand, List<Card> oppCaravan1, List<Card> oppCaravan2, List<Card> oppCaravan3, List<Card> userCaravan1, List<Card> userCaravan2, List<Card> userCaravan3, int userCaravanARank, int userCaravanBRank, int userCaravanCRank, int oppCaravanARank, int oppCaravanBRank, int oppCaravanCRank, List<Card> userCaravanAFaceCards, List<Card> userCaravanBFaceCards, List<Card> userCaravanCFaceCards, List<Card> oppCaravanAFaceCards, List<Card> oppCaravanBFaceCards, List<Card> oppCaravanCFaceCards,List<Card>discard) {
        boolean played = false;
        int counter = 0;
        oppCaravans.clear();
        oppCaravansRanks.clear();
        oppCaravanFaceList.clear();
        userCaravansRanks.clear();
        oppCaravanFaceList.add(oppCaravanAFaceCards);
        oppCaravanFaceList.add(oppCaravanBFaceCards);
        oppCaravanFaceList.add(oppCaravanCFaceCards);
        oppCaravans.add(oppCaravan1);
        oppCaravans.add(oppCaravan2);
        oppCaravans.add(oppCaravan3);
        oppCaravansRanks.add(oppCaravanARank);
        oppCaravansRanks.add(oppCaravanBRank);
        oppCaravansRanks.add(oppCaravanCRank);
        userCaravansRanks.add(userCaravanARank);
        userCaravansRanks.add(userCaravanBRank);
        userCaravansRanks.add(userCaravanCRank);


        System.out.println(userCaravansRanks);
        int rankIndex = 0;
        //check for empty caravans
        for (List<Card> list : oppCaravans) {
            if (played) {
                break;
            }
            if (list.isEmpty()) {
                for (int i = 0; i < computerhand.size(); i++) {
                    if (computerhand.get(i).getRank() <= 10) {

                        list.add(0,computerhand.get(i));
                        computerhand.remove(i);
                        played = true;
                        break;
                        //find the biggest card
                        //

                    }

                }
            }


        }
        //check if caravan < 20
        rankIndex=0;
        for(List<Card> list : oppCaravans){
            if(played){
                break;
            }
            if(oppCaravansRanks.get(rankIndex)<20&&!played) {
                if (list.size() <= 1) {
                    for (int i = 0; i < computerhand.size(); i++) {
                        if (computerhand.get(i).getScoreValue() + oppCaravansRanks.get(rankIndex) < 27 && computerhand.get(i).getScoreValue() != 0) {
                            if(list.get(0).getScoreValue()==computerhand.get(i).getScoreValue()){
                                continue;
                            }
                            list.add(0,computerhand.get(i));
                            computerhand.remove(i);
                            played = true;
                            break;
                        }


                    }


                }
                else {

                    for (int i = 0; i < computerhand.size(); i++) {
                        if (list.get(1).getScoreValue() - list.get(0).getScoreValue() > 0 && list.get(0).getScoreValue() - computerhand.get(i).getScoreValue() > 0) {
                            if (computerhand.get(i).getScoreValue() + oppCaravansRanks.get(rankIndex) < 27 && computerhand.get(i).getScoreValue() != 0) {
                                list.add(0,computerhand.get(i));
                                computerhand.remove(i);

                                played = true;
                                break;
                            }

                        }
                        else if(list.get(1).getScoreValue() - list.get(0).getScoreValue() < 0 && list.get(0).getScoreValue() - computerhand.get(i).getScoreValue() < 0) {
                            if (computerhand.get(i).getScoreValue() + oppCaravansRanks.get(rankIndex) < 27 && computerhand.get(i).getScoreValue() != 0) {
                                list.add(0,computerhand.get(i));
                                computerhand.remove(i);

                                played = true;
                                break;
                            }

                        }
                    }


                }

            }
            rankIndex=rankIndex+1;
            }



        //if its in range 20->27 make sure its beating the opposing(user) caravan
        rankIndex = 0;
        for(List<Card> list : oppCaravans){
            if(played){
                break;
            }
            if (oppCaravansRanks.get(rankIndex) > 20 && oppCaravansRanks.get(rankIndex) < 27) {
                System.out.println(oppCaravansRanks.get(rankIndex));
                System.out.println(userCaravansRanks.get(rankIndex));
                if (oppCaravansRanks.get(rankIndex) < userCaravansRanks.get(rankIndex)) {
                    for (int i = 0; i < computerhand.size(); i++) {
                        if (computerhand.get(i).getScoreValue() + oppCaravansRanks.get(rankIndex) < 27&&computerhand.get(i).getScoreValue()!=0) {
                            list.add(0,computerhand.get(i));
                            computerhand.remove(i);

                            rankIndex=rankIndex+1;
                            played = true;
                            break;


                        }


                    }

                }
                else{
                    rankIndex=rankIndex+1;

                }

            }
            else{
                rankIndex=rankIndex+1;
            }

        }
        rankIndex=0;
        for(List<Card>list:oppCaravans) {
            if(played){
                break;
            }
            if (oppCaravansRanks.get(rankIndex) > 27 && !played) {
                for (int i = 0; i < computerhand.size(); i++) {
                    if (oppCaravanFaceList.get(rankIndex).size() == 0) {

                        if (computerhand.get(i).getRank() == 12) {
                            oppCaravanFaceList.get(rankIndex).add(computerhand.get(i));
                            computerhand.remove(i);

                            played = true;
                            break;

                        }


                    } else if (computerhand.get(i).getRank() == 11) {
                        oppCaravanFaceList.get(rankIndex).add(computerhand.get(i));
                        played = true;
                        break;


                    }
//                    else{
//                        rankIndex=rankIndex+1;
//                    }

                }

            }
            else{
                rankIndex=rankIndex+1;
            }
        }
        rankIndex=0;
        //if the ai cant place a card and the last card is a 10 or an ace discard caravan
        for(List<Card>list:oppCaravans){
            if(played){
                break;
            }
            if(oppCaravansRanks.get(rankIndex)<21&&list.get(0).getScoreValue()==1||list.get(0).getScoreValue()==10){
                for (int i = 0; i < computerhand.size(); i++) {
                    if(computerhand.get(i).getRank()==10&&oppCaravansRanks.get(rankIndex)+computerhand.get(i).getScoreValue()<27){
                        oppCaravanFaceList.get(rankIndex).add(computerhand.get(i));
                        played=true;
                    }
                }
                if(!played){
                    list.clear();
                    played=true;
                    break;
                }




            }

        }

        //if he cant make a move discard
        if(!played){
            discard.add(computerhand.get(0));
            computerhand.remove(0);
        }



//
//
//
//         }
        }
//        if rank is  20>27 &&> my rank
//        else if rank > 27 play queen if no queen remove caravan
//        else if cararank <20 && cararank+cararank[0] <27
//        play king
//        else if can play card where rank<27
//        else next caravan or kill caravan
//
//         play the closest card with the longest range 2...3 not 2...1
//
//return a 4 string stmt for face cards then check length back in gameview


    }







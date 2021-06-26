package com.example.childdevelopment.overview

import com.example.childdevelopment.network.AgesOption
import com.example.childdevelopment.network.MilestonesOption
import java.util.*


val allAgesList: List<AgesOption> =
        listOf(
                AgesOption(0, "0 to 1 month"),
                AgesOption(1,"1 to 2 months"),
                AgesOption(2,"2 to 4 months"),
                AgesOption(3,"4 to 6 months"),
                AgesOption(4, "6 to 9 months"),
                AgesOption(5,"9 to 12 months"),
                AgesOption(6,"12 to 18 months"),
                AgesOption(7, "18 to 24 months"),
                AgesOption(8, "2 to 3 years"),
                AgesOption(9, "3 to 4 years"),
                AgesOption(10, "4 to 5 years")
        )

val allMilestonesList: List<MilestonesOption> =
        listOf(
                MilestonesOption(0, "Makes jerky, quivering arm thrusts¹", "Physical", allAgesList[0].ageRange, listOf("Hold baby’s hands and clap them together while you play music and sing.³")),
                MilestonesOption(1, "Brings hands within range of eyes and mouth¹", "Physical", allAgesList[0].ageRange, listOf("\"Help your baby learn to calm themselves. It’s okay for them to suck on\n" +
                        "their fingers.²\"", "Hold baby’s hands and clap them together while you play music and sing.³")),
                MilestonesOption(2, "Moves head from side to side while lying on stomach¹", "Physical", allAgesList[0].ageRange, listOf("Lay your baby on their tummy when they is awake and put toys near them (tummy time!)²", "Encourage your baby to lift their head by holding toys at eye level in front of them or talking to them during tummy time.²")),
                MilestonesOption(3, "Can briefly calm themselves (may bring hands to mouth and suck on hand)²", "Emotional", allAgesList[1].ageRange, listOf("Pay attention to your baby’s different cries so that you learn to know what they wants.²", "Hold baby’s hands and clap them together while you play music and sing.³")),
                MilestonesOption(4, "Lets you know if he/she is happy or sad²", "Emotional", allAgesList[2].ageRange, listOf("Hold and talk to your baby; smile and be cheerful while you do.²", "Pay close attention to what your baby likes and doesn’t like; you will know how best to meet their needs and what you can do to make your baby happy.²")),
                MilestonesOption(5, "Responds to other people’s emotions and often seems happy²", "Emotional", allAgesList[3].ageRange, listOf("Learn to read your baby’s moods. If he’s happy, keep doing what you are doing. If he’s upset, take a break and comfort your baby.²", "Show your baby how to comfort themselves when they are upset. They may suck on their fingers to self soothe.²", "Gently touch and tickle baby to make them giggle.³")),
                MilestonesOption(6, "Understands “no”²", "Intellectual", allAgesList[4].ageRange, listOf("Ask for behaviors that you want. For example, instead of saying “don’t stand,” say “time to sit.”²", "Read and talk to your baby. ²", "Describe your actions as you dress, feed, and bathe your child. Talk about where you’re going and what you’re doing.³")),
                MilestonesOption(7, "Hands you a book when they wants to hear a story²", "Intellectual", allAgesList[5].ageRange, listOf("Read with your child every day. Have your child turn the pages. Take turns labeling pictures with your child.²")),
                MilestonesOption(8, "Likes to hand things to others as play²", "Social", allAgesList[6].ageRange, listOf()),
                MilestonesOption(9, "Copies others, especially adults and older children²", "Social", allAgesList[7].ageRange, listOf()),
                MilestonesOption(10, "Can name most familiar things ²", "Intellectual", allAgesList[8].ageRange, listOf("Read to your child every day. Ask your child to point to things in the pictures and repeat words after you.²", "Play matching games. Ask your child to find objects in books or around the house that are the same.²")),
                MilestonesOption(11, "Enjoys doing new things²¹", "Emotional", allAgesList[9].ageRange, listOf("Take time to answer your child’s “why” questions. If you don’t know the answer, say “I don’t know,” or help your child find the answer in a book, on the Internet, or from another adult.²")),
                MilestonesOption(12, "Wants to please friends²", "Social",
                        allAgesList[10].ageRange, listOf("Continue to arrange play dates, trips to the park, or play groups. Give your child more freedom to choose activities to play with friends, and let your child work out problems on her own.²"))
        )
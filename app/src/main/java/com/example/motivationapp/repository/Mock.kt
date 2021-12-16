package com.example.motivationapp.repository

import com.example.motivationapp.infra.MotivationAppConstants
import kotlin.random.Random

data class Phrase(val description: String, val phraseCategory: Int)

class Mock {

    private val ALL: Int = MotivationAppConstants.PHRASES_FILTER.ALL
    private val HAPPY: Int = MotivationAppConstants.PHRASES_FILTER.HAPPY
    private val MORNING: Int = MotivationAppConstants.PHRASES_FILTER.MORNING

    private val mListPhrases: List<Phrase> = listOf(
        Phrase("Não sabendo que era impossível, foi lá e fez.", HAPPY),
        Phrase("Você não é derrotado quando perde, você é derrotado quando desiste!", HAPPY),
        Phrase("Quando está mais escuro, vemos mais estrelas!", HAPPY),
        Phrase("Insanidade é fazer sempre a mesma coisa e esperar um resultado diferente.", HAPPY),
        Phrase("Não pare quando estiver cansado, pare quando tiver terminado.", HAPPY),
        Phrase("O que você pode fazer agora que tem o maior impacto sobre o seu sucesso?", HAPPY),
        Phrase("A melhor maneira de prever o futuro é inventá-lo.", MORNING),
        Phrase("Você perde todas as chances que você não aproveita.", MORNING),
        Phrase("Fracasso é o condimento que dá sabor ao sucesso.", MORNING),
        Phrase(" Enquanto não estivermos comprometidos, haverá hesitação!", MORNING),
        Phrase("Se você não sabe onde quer ir, qualquer caminho serve.", MORNING),
        Phrase("Se você acredita, faz toda a diferença.", MORNING),
        Phrase("Riscos devem ser corridos, porque o maior perigo é não arriscar nada!", MORNING)
    )

    fun getPhrase(category: Int): String {

        // Get a phrase with selected filter or the ALL filter will be default
        val filteredPhrase =
            mListPhrases.filter { (it.phraseCategory == category || category == ALL) }

        // Random number to select a phrase on list
        val randonNumberOnList: Int = Random.nextInt(mListPhrases.size)

        // Return the phrase that have the position on list equals the generated number
        return filteredPhrase[randonNumberOnList].description
    }
}
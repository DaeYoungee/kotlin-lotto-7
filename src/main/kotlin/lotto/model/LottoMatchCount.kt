package lotto.model

import lotto.controller.MatchingLottoCount

// 구입한 로또중 당첨된 로또의 개수를 갖고 있는 클래스
object LottoMatchCount {
    private var matchCount: MutableMap<MatchingLottoCount, Int> = mutableMapOf()

    fun matchLotto(lottos: List<Lotto>, winLotteryNumber: Lotto?, bonusLotteryNumber: Int): Map<MatchingLottoCount, Int> {
        val matchLottoNumber = MutableList(lottos.size) { 0 to false }
        for ((ind, lotto) in lottos.withIndex()) {
            matchLottoNumber[ind] = winLotteryNumber?.let {
                lotto.getMatchCount(it) to lotto.isMatchBonusNumber(bonusLotteryNumber)
            }!!
        }

        for (i in matchLottoNumber) {
            increaseMatchCount(i.first + isMatchBonusNumber(i.second), i.second)
        }

        return getMatchCount()
    }

    private fun increaseMatchCount(
        matchCount: Int,
        isMatchBonus: Boolean,
    ) {
        when (matchCount) {
            3 -> increaseCount(MatchingLottoCount.THREE)
            4 -> increaseCount(MatchingLottoCount.FOUR)
            5 -> increaseCount(MatchingLottoCount.FIVE)
            6 -> {
                if (isMatchBonus) {
                    increaseCount(MatchingLottoCount.FIVE_BONUS)
                    return
                }
                increaseCount(MatchingLottoCount.SIX)
            }
        }
    }

    private fun isMatchBonusNumber(isMatchBonusCount: Boolean): Int {
        if (isMatchBonusCount) return 1
        return 0
    }

    fun increaseCount(c: MatchingLottoCount) {
        matchCount[c] = (matchCount[c] ?: 0) + 1
    }

    fun getMatchCount() = matchCount.toMap()
}
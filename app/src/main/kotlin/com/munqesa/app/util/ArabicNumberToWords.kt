package com.munqesa.app.util

object ArabicNumberToWords {

    private val units = arrayOf(
        "", "واحد", "اثنان", "ثلاثة", "أربعة", "خمسة", "ستة", "سبعة", "ثمانية", "تسعة",
        "عشرة", "أحد عشر", "اثنا عشر", "ثلاثة عشر", "أربعة عشر", "خمسة عشر",
        "ستة عشر", "سبعة عشر", "ثمانية عشر", "تسعة عشر"
    )

    private val tens = arrayOf(
        "", "", "عشرون", "ثلاثون", "أربعون", "خمسون", "ستون", "سبعون", "ثمانون", "تسعون"
    )

    private val hundreds = arrayOf(
        "", "مائة", "مائتان", "ثلاثمائة", "أربعمائة", "خمسمائة",
        "ستمائة", "سبعمائة", "ثمانمائة", "تسعمائة"
    )

    /** المبلغ بالحروف بالريال اليمني (بدون كسور لأن الفلس غير متداول). */
    fun convertRialToWords(amount: Double): String {
        val rials = Math.round(amount)
        val text = if (rials > 0) convertNumber(rials) else "صفر"
        return "$text ريال يمني فقط لا غير"
    }

    private fun convertNumber(number: Long): String {
        if (number == 0L) return "صفر"
        if (number < 0) return "سالب ${convertNumber(-number)}"

        return convertRecursive(number).trim()
    }

    private fun convertRecursive(number: Long): String {
        return when {
            number < 20 -> units[number.toInt()]
            number < 100 -> {
                val ten = (number / 10).toInt()
                val unit = number % 10
                if (unit == 0L) tens[ten]
                else "${units[unit.toInt()]} و${tens[ten]}"
            }
            number < 1000 -> {
                val hundred = (number / 100).toInt()
                val remainder = number % 100
                if (remainder == 0L) hundreds[hundred]
                else "${hundreds[hundred]} و${convertRecursive(remainder)}"
            }
            number < 1000000 -> {
                val thousand = number / 1000
                val remainder = number % 1000
                val thousandText = when {
                    thousand == 1L -> "ألف"
                    thousand == 2L -> "ألفان"
                    thousand >= 3L && thousand <= 10L -> "${convertRecursive(thousand)} آلاف"
                    else -> "${convertRecursive(thousand)} ألف"
                }
                if (remainder == 0L) thousandText
                else "$thousandText و${convertRecursive(remainder)}"
            }
            else -> {
                val million = number / 1000000
                val remainder = number % 1000000
                val millionText = when {
                    million == 1L -> "مليون"
                    million == 2L -> "مليونان"
                    million >= 3L && million <= 10L -> "${convertRecursive(million)} ملايين"
                    else -> "${convertRecursive(million)} مليون"
                }
                if (remainder == 0L) millionText
                else "$millionText و${convertRecursive(remainder)}"
            }
        }
    }
}

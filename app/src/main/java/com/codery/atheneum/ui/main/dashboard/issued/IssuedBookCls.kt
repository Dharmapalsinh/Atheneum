package com.codery.atheneum.ui.main.dashboard.issued

import java.time.LocalDate

data class IssuedBookCls(val bookId : String, val issuedOn : LocalDate, val dueOn : LocalDate, val submittedOn : LocalDate?)
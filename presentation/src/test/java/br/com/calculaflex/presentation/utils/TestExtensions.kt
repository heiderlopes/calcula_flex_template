package br.com.calculaflex.presentation.utils

import io.mockk.MockKVerificationScope
import io.mockk.coVerify
import io.mockk.verify

fun verifyOnce(verifyBlock: MockKVerificationScope.() -> Unit) = verify(exactly = 1, verifyBlock = verifyBlock)

fun coVerifyOnce(verifyBlock: suspend MockKVerificationScope.() -> Unit) = coVerify(exactly = 1, verifyBlock = verifyBlock)

package com.sampleproject.utils.base.mvi

/**
 * States of UI
 */
interface UiState

/**
 * UI events
 */
interface UiAction

/**
 * Effects which can be used to state
 * Effect is a data-less action, which results in cosmetic ui changes
 * DOES NOT affect data state
 */
interface UiEffect

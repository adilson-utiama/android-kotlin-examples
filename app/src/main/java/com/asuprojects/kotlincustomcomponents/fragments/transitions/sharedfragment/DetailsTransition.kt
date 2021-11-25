package com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedfragment

import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet

class DetailsTransition : TransitionSet() {

    /**
     * This transition is just a set of three transitions that will play together:
     * - ChangeBounds : animates the bounds (location and size) of the view.
     * - ChangeTransform : animates the scale of the view, including the parent.
     * - ChangeImageTransform : allows us to change the size (and/or scale type) of the image
     */

    init {
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds())
            .addTransition(ChangeTransform())
            .addTransition(ChangeImageTransform())


    }
}
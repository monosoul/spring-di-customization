package com.github.monosoul.fortuneteller.common;

import java.util.Map;

import static com.github.monosoul.fortuneteller.common.ZodiacSign.*;

public interface Horoscopes {
    Map<ZodiacSign, String> HOROSCOPES = Map.of(
            AQUARIUS, "Inspiration for writing, music, drawing, painting, or other creative activities could " +
                    "hover just out of reach today, Aquarius. Great ideas could pop into your head and out just as " +
                    "quickly. The only way to avoid the frustration of missing out on wonderful ideas is to write " +
                    "them down as soon as they come. Making notes can also stimulate further inspiration. Go for it! ",
            ARIES, "A current or potential love relationship could hit a snag as you have a clash of wills. " +
                    "If either of you is stubborn, this could turn into an unpleasant power struggle. Try to work " +
                    "out a course of action that creates a win/win situation, so neither of you feels compromised. " +
                    "In this way, the development of your relationship will progress rather than regress.",
            CANCER, "Visitors who mean a lot to you can make your home an even warmer and cozier place, Cancer. " +
                    "You'll probably spend a lot of time fixing it up and receive sincere compliments. The only " +
                    "downside might be that your current partner may not be there due to circumstances beyond " +
                    "anyone's control. Enjoy the visit, and invite your friends to return when your partner is there.",
            CAPRICORN, "Are you romantically attracted to someone you know through work, Capricorn? If so, this " +
                    "isn't a good time to pursue it. You might be wearing your heart on your sleeve a little too " +
                    "obviously. This could be unsettling for your friend, and it might sabotage the results you're " +
                    "hoping for. Be patient and let the relationship develop. Your feelings could well be mutual!",
            GEMINI, "Circumstances beyond your control may frustrate your desire to get together with a love " +
                    "partner today, Gemini. Work or family obligations could interfere. If your partner has to " +
                    "beg off, don't get upset and start doling out blame. This won't help and could put your " +
                    "friend on the defensive. Make arrangements to meet another day. Absence makes the heart grow " +
                    "fonder!",
            LEO, "A long but necessary phone call from a close friend or love partner could come at work " +
                    "today, Leo. Matters of immediate concern need resolution. You might be uneasy about taking " +
                    "company time, and could even attract some unsettling attention, but you need to have this " +
                    "conversation now. It probably won't have any long-term negative effects, so do what you have " +
                    "to do. You'll feel a lot better."
    );
}

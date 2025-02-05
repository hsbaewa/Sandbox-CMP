package kr.co.hs.sandbox.cmp.ui.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme,
        content = {
            CompositionLocalProvider(
                LocalRippleConfiguration provides RippleConfiguration(
                    MaterialTheme.colorScheme.surfaceBright,
                    RippleAlpha(
                        .5f,
                        .3f,
                        .3f,
                        .5f
                    )
                )
            ) {
                content()
            }
        }
    )
}


private val lightColorScheme = lightColorScheme(
    /**
     * <primary>
     *     Button BG
     */
    primary = md_color_blue_gray_900,

    /**
     * <surfaceContainerHighest>
     *     스위치 off 배경
     */
    surfaceContainerHighest = md_color_blue_gray_100,

    /**
     * <outline>
     *     스위치 off 테두리
     */
    outline = md_color_blue_gray_800,

    /**
     * <onPrimary>
     *     Button font Tint
     */
    onPrimary = md_color_blue_gray_100,

    /**
     * <inversePrimary>
     *     SecFileDialogTextButton disabled content color
     */
    inversePrimary = md_color_blue_gray_400,

    /**
     * <onSurface>
     *     TopBar font
     *     TopBar navigation icon tint
     *     Button disabled BG(with alpha)
     *     Button disabled font tint(with alpha)
     */
    onSurface = md_color_blue_gray_100,

    /**
     * <surface>
     *     TopBar BG
     */
    surface = md_color_blue_gray_900,

    /**
     * <background>
     *     default BG
     */
    background = md_color_blue_gray_050,

    /**
     * <surfaceContainer>
     *     NavigationBar BG
     *     SelectableItem selected BG (with alpha)
     */
    surfaceContainer = md_color_blue_gray_100,

    /**
     * <onSecondaryContainer>
     *     NavigationBarItem selected icon Tint
     */
    onSecondaryContainer = md_color_blue_gray_500,

    /**
     * <secondaryContainer>
     *     NavigationBarItem selected icon BG
     *     NavigationBarItem selected font
     */
    secondaryContainer = md_color_blue_gray_100,

    /**
     * <onSurfaceVariant>
     *     TopBar action button tint
     *     NavigationBarItem contents
     */
    onSurfaceVariant = md_color_blue_gray_700,

    /**
     * <secondary>
     *     DropdownMenu BG
     */
    secondary = md_color_blue_gray_200,

    /**
     * <onSecondary>
     *     DropdownMenuItem font
     */
    onSecondary = md_color_blue_gray_900,

    /**
     * ripple effect
     */
    surfaceBright = md_color_blue_gray_300
)
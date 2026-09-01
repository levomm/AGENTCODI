package de.agentcodi.app;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

final class UiTheme {
    final int page;
    final int surface;
    final int surfaceRaised;
    final int primary;
    final int secondary;
    final int accent;
    final int border;
    final int danger;
    final boolean dark;

    private final Context context;

    UiTheme(Context context) {
        this.context = context;
        int nightMode = context.getResources().getConfiguration().uiMode
            & Configuration.UI_MODE_NIGHT_MASK;
        dark = nightMode == Configuration.UI_MODE_NIGHT_YES;
        if (dark) {
            page = 0xFF0B0B0A;
            surface = 0xFF11110F;
            surfaceRaised = 0xFF1A1917;
            primary = 0xFFE9E6E0;
            secondary = 0xFF77736D;
            accent = 0xFF9A4D3C;
            border = 0xFF2A2824;
            danger = 0xFFC96F5E;
        } else {
            page = 0xFFF4F1EC;
            surface = 0xFFFBF9F5;
            surfaceRaised = 0xFFF0ECE5;
            primary = 0xFF1D1C1A;
            secondary = 0xFF6F6A63;
            accent = 0xFF8A4436;
            border = 0xFFD8D2C9;
            danger = 0xFFA83F32;
        }
    }

    int dp(int value) {
        return Math.round(value * context.getResources().getDisplayMetrics().density);
    }

    GradientDrawable background(int fill, int stroke, int radiusDp) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(fill);
        drawable.setCornerRadius(dp(radiusDp));
        if (stroke != Color.TRANSPARENT) {
            drawable.setStroke(dp(1), stroke);
        }
        return drawable;
    }

    TextView text(String value, int sizeSp, int color) {
        TextView view = new TextView(context);
        view.setText(value);
        view.setTextSize(sizeSp);
        view.setTextColor(color);
        return view;
    }

    TextView sectionLabel(String value) {
        TextView label = text(value, 12, secondary);
        label.setTypeface(Typeface.DEFAULT_BOLD);
        label.setLetterSpacing(0.04f);
        return label;
    }

    TextView body(String value) {
        TextView body = text(value, 15, primary);
        body.setLineSpacing(0.0f, 1.18f);
        return body;
    }

    LinearLayout card() {
        LinearLayout card = new LinearLayout(context);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(16), dp(16), dp(16));
        card.setBackground(background(surface, border, 14));
        return card;
    }

    Button primaryButton(String label) {
        Button button = baseButton(label);
        button.setTextColor(0xFFF7F3EE);
        button.setBackground(background(accent, Color.TRANSPARENT, 10));
        return button;
    }

    Button secondaryButton(String label) {
        Button button = baseButton(label);
        button.setTextColor(dark ? 0xFFB8B2AA : accent);
        button.setBackground(background(surfaceRaised, border, 10));
        return button;
    }

    Button compactButton(String label) {
        Button button = secondaryButton(label);
        button.setMinHeight(dp(40));
        button.setMinimumHeight(dp(40));
        button.setPadding(dp(12), 0, dp(12), 0);
        button.setTextSize(14);
        return button;
    }

    ImageButton iconButton(int iconResource, String description) {
        return baseIconButton(
            iconResource,
            description,
            surfaceRaised,
            border,
            dark ? 0xFFAAA49C : accent
        );
    }

    ImageButton primaryIconButton(int iconResource, String description) {
        return baseIconButton(
            iconResource,
            description,
            accent,
            Color.TRANSPARENT,
            0xFFF7F3EE
        );
    }

    ImageButton dangerIconButton(int iconResource, String description) {
        return baseIconButton(
            iconResource,
            description,
            surfaceRaised,
            border,
            danger
        );
    }

    void setIcon(ImageButton button, int iconResource, String description) {
        button.setImageResource(iconResource);
        button.setContentDescription(description);
        button.setTooltipText(description);
    }

    void setEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        view.setAlpha(enabled ? 1.0f : 0.45f);
    }

    void addWithTopMargin(LinearLayout parent, View child, int marginDp) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.topMargin = dp(marginDp);
        parent.addView(child, params);
    }

    private Button baseButton(String label) {
        Button button = new Button(context);
        button.setText(label);
        button.setTextSize(15);
        button.setAllCaps(false);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setMinHeight(dp(46));
        button.setMinimumHeight(dp(46));
        return button;
    }

    private ImageButton baseIconButton(
        int iconResource,
        String description,
        int fill,
        int stroke,
        int iconColor
    ) {
        ImageButton button = new ImageButton(context);
        button.setImageResource(iconResource);
        button.setColorFilter(iconColor);
        button.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);
        button.setPadding(dp(11), dp(11), dp(11), dp(11));
        button.setMinimumWidth(dp(46));
        button.setMinimumHeight(dp(46));
        button.setBackground(iconBackground(fill, stroke));
        button.setContentDescription(description);
        button.setTooltipText(description);
        return button;
    }

    private Drawable iconBackground(int fill, int stroke) {
        GradientDrawable content = background(fill, stroke, 10);
        GradientDrawable mask = background(Color.WHITE, Color.TRANSPARENT, 10);
        int ripple = dark ? 0x22FFFFFF : 0x18000000;
        return new RippleDrawable(ColorStateList.valueOf(ripple), content, mask);
    }
}

package br.com.tratto.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import br.com.tratto.R;

import static android.content.ContentValues.TAG;

/**
 * Created by lily on 8/11/17.
 */

public class FontEditTextMasked extends AppCompatEditText implements TextWatcher {

    public static final String SPACE = " ";
    private String mask;
    private char charRepresentation;
    private boolean keepHint;
    private int[] rawToMask;
    private RawText rawText;
    private boolean editingBefore;
    private boolean editingOnChanged;
    private boolean editingAfter;
    private int[] maskToRaw;
    private int selection;
    private boolean initialized;
    private boolean ignore;
    protected int maxRawLength;
    private int lastValidMaskPosition;
    private boolean selectionChanged;
    private OnFocusChangeListener focusChangeListener;
    private String allowedChars;
    private String deniedChars;

    public FontEditTextMasked(Context context) {
        super(context);
        init(context, null);
    }

    public FontEditTextMasked(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FontEditTextMasked(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            return false;
        }

        setTypeface(tf);
        return true;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        final Parcelable superParcellable = super.onSaveInstanceState();
        final Bundle state = new Bundle();
        state.putParcelable("super", superParcellable);
        state.putString("text", getRawText());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        super.onRestoreInstanceState(((Bundle) state).getParcelable("super"));
        final String text = bundle.getString("text");

        Log.d(TAG, "onRestoreInstanceState: " + text);
        setText(text);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
//		if (text == null || text.equals("")) return;
        super.setText(text, type);
    }

    /**
     * @param listener - its onFocusChange() method will be called before performing MaskedEditText operations,
     *                 related to this event.
     */
    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener listener) {
        focusChangeListener = listener;
    }

    private void cleanUp() {
        initialized = false;

        generatePositionArrays();

        rawText = new RawText();
        selection = rawToMask[0];

        editingBefore = true;
        editingOnChanged = true;
        editingAfter = true;
        if (hasHint() && rawText.length() == 0) {
//            this.setText(makeMaskedTextWithHint());
        } else {
            this.setText(makeMaskedText());
        }
        editingBefore = false;
        editingOnChanged = false;
        editingAfter = false;

        maxRawLength = maskToRaw[previousValidPosition(mask.length() - 1)] + 1;
        lastValidMaskPosition = findLastValidMaskPosition();
        initialized = true;

        super.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (focusChangeListener != null) {
                    focusChangeListener.onFocusChange(v, hasFocus);
                }

                if (hasFocus()) {
                    selectionChanged = false;
                    setSelection(lastValidPosition());
                }
            }
        });
    }

    private int findLastValidMaskPosition() {
        for (int i = maskToRaw.length - 1; i >= 0; i--) {
            if (maskToRaw[i] != -1) return i;
        }
        throw new RuntimeException("Mask must contain at least one representation char");
    }

    private boolean hasHint() {
        return getHint() != null;
    }

    public void setMask(String mask) {
        this.mask = mask;
        cleanUp();
    }

    public String getMask() {
        return this.mask;
    }

    public String getRawText() {
        return this.rawText.getText();
    }

    public void setCharRepresentation(char charRepresentation) {
        this.charRepresentation = charRepresentation;
        cleanUp();
    }

    public char getCharRepresentation() {
        return this.charRepresentation;
    }

    /**
     * Generates positions for values characters. For instance:
     * Input data: mask = "+7(###)###-##-##
     * After method execution:
     * rawToMask = [3, 4, 5, 6, 8, 9, 11, 12, 14, 15]
     * maskToRaw = [-1, -1, -1, 0, 1, 2, -1, 3, 4, 5, -1, 6, 7, -1, 8, 9]
     * charsInMask = "+7()- " (and space, yes)
     */
    private void generatePositionArrays() {
        int[] aux = new int[mask.length()];
        maskToRaw = new int[mask.length()];
        String charsInMaskAux = "";

        int charIndex = 0;
        for (int i = 0; i < mask.length(); i++) {
            char currentChar = mask.charAt(i);
            if (currentChar == charRepresentation) {
                aux[charIndex] = i;
                maskToRaw[i] = charIndex++;
            } else {
                String charAsString = Character.toString(currentChar);
                if (!charsInMaskAux.contains(charAsString)) {
                    charsInMaskAux = charsInMaskAux.concat(charAsString);
                }
                maskToRaw[i] = -1;
            }
        }
        if (charsInMaskAux.indexOf(' ') < 0) {
            charsInMaskAux = charsInMaskAux + SPACE;
        }

        char[] charsInMask = charsInMaskAux.toCharArray();

        rawToMask = new int[charIndex];
        for (int i = 0; i < charIndex; i++) {
            rawToMask[i] = aux[i];
        }
    }

    private void init(Context context, AttributeSet attrs) {
        addTextChangedListener(this);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FontEditTextMasked);
        mask = attributes.getString(R.styleable.FontEditTextMasked_mask);

        allowedChars = attributes.getString(R.styleable.FontEditTextMasked_allowed_chars);
        deniedChars = attributes.getString(R.styleable.FontEditTextMasked_denied_chars);

        String representation = attributes.getString(R.styleable.FontEditTextMasked_char_representation);

        if (representation == null) {
            charRepresentation = '#';
        } else {
            charRepresentation = representation.charAt(0);
        }

        keepHint = attributes.getBoolean(R.styleable.FontEditTextMasked_keep_hint, false);

        cleanUp();

        // Ignoring enter key presses
        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    default:
                        return true;
                }
            }
        });

        String customFont = attributes.getString(R.styleable.FontEditTextMasked_customFontE);
        setCustomFont(context, customFont);

        attributes.recycle();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        if (!editingBefore) {
            editingBefore = true;
            if (start > lastValidMaskPosition) {
                ignore = true;
            }
            int rangeStart = start;
            if (after == 0) {
                rangeStart = erasingStart(start);
            }
            Range range = calculateRange(rangeStart, start + count);
            if (range.getStart() != -1) {
                rawText.subtractFromString(range);
            }
            if (count > 0) {
                selection = previousValidPosition(start);
            }
        }
    }

    private int erasingStart(int start) {
        while (start > 0 && maskToRaw[start] == -1) {
            start--;
        }
        return start;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!editingOnChanged && editingBefore) {
            editingOnChanged = true;
            if (ignore) {
                return;
            }
            if (count > 0) {
                int startingPosition = maskToRaw[nextValidPosition(start)];
                String addedString = s.subSequence(start, start + count).toString();
                count = rawText.addToString(clear(addedString), startingPosition, maxRawLength);
                if (initialized) {
                    int currentPosition;
                    if (startingPosition + count < rawToMask.length)
                        currentPosition = rawToMask[startingPosition + count];
                    else
                        currentPosition = lastValidMaskPosition + 1;
                    selection = nextValidPosition(currentPosition);
                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!editingAfter && editingBefore && editingOnChanged) {
            editingAfter = true;
            if (hasHint() && (keepHint || rawText.length() == 0)) {
//                setText(makeMaskedTextWithHint());
            } else {
                setText(makeMaskedText());
            }

            selectionChanged = false;
            setSelection(selection);

            editingBefore = false;
            editingOnChanged = false;
            editingAfter = false;
            ignore = false;
        }
    }

    public boolean isKeepHint() {
        return keepHint;
    }

    public void setKeepHint(boolean keepHint) {
        this.keepHint = keepHint;
        setText(getRawText());
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        // On Android 4+ this method is being called more than 1 time if there is a hint in the EditText, what moves the cursor to left
        // Using the boolean var selectionChanged to limit to one execution

        if (initialized) {
            if (!selectionChanged) {
                selStart = fixSelection(selStart);
                selEnd = fixSelection(selEnd);

                // exactly in this order. If getText.length() == 0 then selStart will be -1
                if (selStart > getText().length()) selStart = getText().length();
                if (selStart < 0) selStart = 0;

                // exactly in this order. If getText.length() == 0 then selEnd will be -1
                if (selEnd > getText().length()) selEnd = getText().length();
                if (selEnd < 0) selEnd = 0;

                setSelection(selStart, selEnd);
                selectionChanged = true;
            } else {
                //check to see if the current selection is outside the already entered text
                if (selStart > rawText.length() - 1) {
                    final int start = fixSelection(selStart);
                    final int end = fixSelection(selEnd);
                    if (start >= 0 && end < getText().length()) {
                        setSelection(start, end);
                    }
                }
            }
        }
        super.onSelectionChanged(selStart, selEnd);
    }

    private int fixSelection(int selection) {
        if (selection > lastValidPosition()) {
            return lastValidPosition();
        } else {
            return nextValidPosition(selection);
        }
    }

    private int nextValidPosition(int currentPosition) {
        while (currentPosition < lastValidMaskPosition && maskToRaw[currentPosition] == -1) {
            currentPosition++;
        }
        if (currentPosition > lastValidMaskPosition) return lastValidMaskPosition + 1;
        return currentPosition;
    }

    private int previousValidPosition(int currentPosition) {
        while (currentPosition >= 0 && maskToRaw[currentPosition] == -1) {
            currentPosition--;
            if (currentPosition < 0) {
                return nextValidPosition(0);
            }
        }
        return currentPosition;
    }

    private int lastValidPosition() {
        if (rawText.length() == maxRawLength) {
            return rawToMask[rawText.length() - 1] + 1;
        }
        return nextValidPosition(rawToMask[rawText.length()]);
    }


    private String makeMaskedText() {
        int maskedTextLength;
        if (rawText.length() < rawToMask.length) {
            maskedTextLength = rawToMask[rawText.length()];
        } else {
            maskedTextLength = mask.length();
        }
        char[] maskedText = new char[maskedTextLength]; //mask.replace(charRepresentation, ' ').toCharArray();
        for (int i = 0; i < maskedText.length; i++) {
            int rawIndex = maskToRaw[i];
            if (rawIndex == -1) {
                maskedText[i] = mask.charAt(i);
            } else {
                maskedText[i] = rawText.charAt(rawIndex);
            }
        }
        return new String(maskedText);
    }

    private CharSequence makeMaskedTextWithHint() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        String hint = "11111111111";
        int mtrv;
        int maskFirstChunkEnd = rawToMask[0];
        for (int i = 0; i < mask.length(); i++) {
            mtrv = maskToRaw[i];
            if (mtrv != -1) {
                if (mtrv < rawText.length()) {
                    ssb.append(rawText.charAt(mtrv));
                } else {
                    ssb.append(hint.charAt(maskToRaw[i]));
                }
            } else {
                ssb.append(mask.charAt(i));
            }
            if ((keepHint && rawText.length() < rawToMask.length && i >= rawToMask[rawText.length()])
                    || (!keepHint && i >= maskFirstChunkEnd)) {
                ssb.setSpan(new ForegroundColorSpan(getCurrentHintTextColor()), i, i + 1, 0);
            }
        }
        return ssb;
    }

    private Range calculateRange(int start, int end) {
        Range range = new Range();
        for (int i = start; i <= end && i < mask.length(); i++) {
            if (maskToRaw[i] != -1) {
                if (range.getStart() == -1) {
                    range.setStart(maskToRaw[i]);
                }
                range.setEnd(maskToRaw[i]);
            }
        }
        if (end == mask.length()) {
            range.setEnd(rawText.length());
        }
        if (range.getStart() == range.getEnd() && start < end) {
            int newStart = previousValidPosition(range.getStart() - 1);
            if (newStart < range.getStart()) {
                range.setStart(newStart);
            }
        }
        return range;
    }

    private String clear(String string) {
        if (deniedChars != null) {
            for (char c : deniedChars.toCharArray()) {
                string = string.replace(Character.toString(c), "");
            }
        }

        if (allowedChars != null) {
            StringBuilder builder = new StringBuilder(string.length());

            for (char c : string.toCharArray()) {
                if (allowedChars.contains(String.valueOf(c))) {
                    builder.append(c);
                }
            }

            string = builder.toString();
        }

        return string;
    }

    public class Range {
        private int start;
        private int end;

        Range() {
            start = -1;
            end = -1;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }

    public class RawText {
        private String text;

        public RawText() {
            text = "";
        }

        /**
         * text = 012345678, range = 123 =&gt; text = 0456789
         *
         * @param range given range
         */
        public void subtractFromString(Range range) {
            String firstPart = "";
            String lastPart = "";

            if (range.getStart() > 0 && range.getStart() <= text.length()) {
                firstPart = text.substring(0, range.getStart());
            }
            if (range.getEnd() >= 0 && range.getEnd() < text.length()) {
                lastPart = text.substring(range.getEnd(), text.length());
            }
            text = firstPart.concat(lastPart);
        }

        /**
         * @param newString New String to be added
         * @param start     Position to insert newString
         * @param maxLength Maximum raw text length
         * @return Number of added characters
         */
        public int addToString(String newString, int start, int maxLength) {
            String firstPart = "";
            String lastPart = "";

            if (newString == null || newString.equals("")) {
                return 0;
            } else if (start < 0) {
                throw new IllegalArgumentException("Start position must be non-negative");
            } else if (start > text.length()) {
                throw new IllegalArgumentException("Start position must be less than the actual text length");
            }

            int count = newString.length();

            if (start > 0) {
                firstPart = text.substring(0, start);
            }
            if (start >= 0 && start < text.length()) {
                lastPart = text.substring(start, text.length());
            }
            if (text.length() + newString.length() > maxLength) {
                count = maxLength - text.length();
                newString = newString.substring(0, count);
            }
            text = firstPart.concat(newString).concat(lastPart);
            return count;
        }

        public String getText() {
            return text;
        }

        public int length() {
            return text.length();
        }

        public char charAt(int position) {
            return text.charAt(position);
        }
    }
}

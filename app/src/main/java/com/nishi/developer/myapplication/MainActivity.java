package com.nishi.developer.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_scope_work_view;
    private TextView edt_scope_work;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        ll_scope_work_view = (LinearLayout) findViewById(R.id.ll_scope_work_view);

        edt_scope_work = (TextView) findViewById(R.id.edt_scope_work);
//        edt_scope_work.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        edt_scope_work.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_scope_work:

                addScopeDialog();

                break;

        }
    }

    private void addScopeDialog() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dialog.setContentView(R.layout.dialog_add_scope);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        LinearLayout ll_main_layout_add_scope = (LinearLayout) dialog.findViewById(R.id.ll_main_layout_add_scope);


        TextView txt_reset = (TextView) dialog.findViewById(R.id.txt_reset);

        LinearLayout ll_close = (LinearLayout) dialog.findViewById(R.id.ll_close);
        ImageView iv_close = (ImageView) dialog.findViewById(R.id.iv_close);
        final Button btn_add = (Button) dialog.findViewById(R.id.btn_add);


        final EditText edt_scope_bullet = (EditText) dialog.findViewById(R.id.edt_scope_bullet);
        //edt_scope_bullet.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(1000)});

        if (edt_scope_work.getText().toString().length() == 0) {
            // edt_scope_bullet.append("\u2022");
        } else {
            edt_scope_bullet.setText(edt_scope_work.getText().toString().trim());
        }

        edt_scope_bullet.setSelection(edt_scope_bullet.getText().length());

        edt_scope_bullet.setOnFocusChangeListener(onFocusChangeListener);
        edt_scope_bullet.setOnFocusChangeListener(onFocusChangeListener);


        if (edt_scope_bullet.getText().length() == 0) {

            edt_scope_bullet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {

                        edt_scope_bullet.append("\u2022");

                        //   edt_scope_bullet.setSelection(edt_scope_bullet.getText().length());
                        Toast.makeText(getApplicationContext(), "Got the focus", Toast.LENGTH_LONG).show();
                    } else {
                        //   Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        edt_scope_bullet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence txt, int start, int before, int count) {

//                Log.i("TC", "onTC " + txt.toString() + " "+ txt.subSequence(start, start + count).toString());

                if (txt.length() == 0) {
                    edt_scope_bullet.setText("\u2022");
                    edt_scope_bullet.setSelection(edt_scope_bullet.getText().length());
                    return;
                } else {

                    if (countBullets(edt_scope_bullet.getText().toString()) > 11) {

                        ErrorDialogView("Maximum 10 bullet points are allowed", false);

                        return;
                    }

                    if (txt.subSequence(start, start + count).toString().equalsIgnoreCase("\n")) {

                        String b = new String(txt.toString());
//                        char first = b.charAt(1);
//                        Toast.makeText(getApplicationContext(), "secornd " + String.valueOf(first), Toast.LENGTH_LONG).show();

                        if (!b.contains("\u2022\n")) {

                            StringBuilder str = new StringBuilder(b);

                            System.out.println("string = " + str);

//    char str1 = txt.charAt(start + before);


                            str.insert(start + count, '\u2022');

                            List<String> myList = new ArrayList<String>(Arrays.asList(str.toString().split("\u2022")));

                            String completeText = "";
                            for (int countTemp = 0; countTemp < myList.size(); countTemp++) {

                                String string = myList.get(countTemp);

                                if (string.length() > 0) {

                                    String capitalText = "";

                                    capitalText = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();

                                    String bulletCapitalText = "\u2022" + capitalText;

                                    completeText = completeText + bulletCapitalText;

                                }


                            }

                            if ((start + count) != completeText.length()) {

                                edt_scope_bullet.setText(completeText);
                                //edt_scope_bullet.append("\u2022");
                                edt_scope_bullet.setSelection(start + count + 1);

                            } else {

                                edt_scope_bullet.setText(completeText + "\u2022");
                                //edt_scope_bullet.append("\u2022");
                                edt_scope_bullet.setSelection(start + count + 1);

                            }
                        } else {

//                            edt_scope_bullet.setImeOptions(EditorInfo.IME_ACTION_DONE);
//                            hideSoftKeyboard(context,edt_scope_bullet);

                           btn_add.performClick();

                        }
                    } else {

                    }

                }


            }

            @Override
            public void afterTextChanged(final Editable editable) {

            }
        });

        txt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edt_scope_bullet.setText("");


            }
        });


        ll_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cc.hideSoftKeyboard(context, edt_scope_bullet);

                btn_add.performClick();
                // dialog.dismiss();
            }
        });


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cc.hideSoftKeyboard(context, edt_scope_bullet);
                //dialog.dismiss();
                btn_add.performClick();
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideSoftKeyboard(context, edt_scope_bullet);

                if (countBullets(edt_scope_bullet.getText().toString()) > 11) {

                    ErrorDialogView("Maximum 10 bullet points are allowed", false);

                    //cc.showToast(CreateNewJobActivity.this, "Maximum 10 bullet points are allowed");
                    return;
                }


                edt_scope_bullet.setText(updateFtrstLetter(edt_scope_bullet.getText().toString()));

                edt_scope_bullet.setSelection(edt_scope_bullet.getText().length());

                if (edt_scope_bullet.getText().toString().trim().length() != 1) {
                    dialog.dismiss();

                    //   Log.e("scope of work ", edt_scope_bullet.getText().toString().trim());


                    String str = edt_scope_bullet.getText().toString().trim();

                    String substring = str.substring(Math.max(str.length() - 2, 0));

                    String tempString = "";

                    if (substring.equalsIgnoreCase("\n" + "•")) {

                        tempString = str.substring(0, str.length() - 2);

                    } else {
                        tempString = str;
                    }

                    edt_scope_work.setText(tempString);

////                    edt_scope_work.setText(f1.substring(0,f1.indexOf('\n')));
//
//                    try {
//                        String s = URLEncoder.encode(edt_scope_bullet.getText().toString().trim(), "UTF-8");
//                        Log.e("encoding ", s);
//
//
//                        String  s2 = encode(edt_scope_bullet.getText().toString().trim());
//                        s2 = s2.replace("\\\\n" , " ");
//
//                        String s1 = URLDecoder.decode(edt_scope_bullet.getText().toString().trim(), "UTF-8");
//
//                        Log.e("decording ", s2);
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }


                } else {
                    edt_scope_bullet.setText("");
                    dialog.dismiss();

                    edt_scope_work.setText("");

                    // cc.showToast(context, "Add project details!");
                }


            }
        });

        ll_main_layout_add_scope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(context, edt_scope_bullet);
            }
        });

        dialog.show();
    }


    public static int countBullets(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        int lines = 1;
        int pos = 0;
        while ((pos = str.indexOf("\u2022", pos) + 1) != 0) {
            lines++;
        }
        return lines;
    }

    public void hideSoftKeyboard(Context context, EditText ed) {

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(ed.getWindowToken(), 0);

    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                ((EditText) view).setSelection(((EditText) view).getText().length());
            }
        }
    };

    private void ErrorDialogView(String message, final boolean isLogout) {

        final iOSDialogCenterView iOSDialog = new iOSDialogCenterView(MainActivity.this);
        iOSDialog.setTitle("OPPS_TITLE");
        iOSDialog.setSubtitle(message);
        // iOSDialog.setNegativeLabel("Cancel");
        iOSDialog.setPositiveLabel("Ok");
        iOSDialog.setBoldPositiveLabel(true);
        iOSDialog.setNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOSDialog.dismiss();
            }
        });
        iOSDialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iOSDialog.dismiss();

            }
        });
        iOSDialog.show();
    }

    private String updateFtrstLetter(String a) {
        String text = a;

        int pos = 0;
        boolean capitalize = true;
        StringBuilder sb = new StringBuilder(text);
        while (pos < sb.length()) {
            if (sb.charAt(pos) == '\u2022') {
                capitalize = true;
            } else if (capitalize && !Character.isWhitespace(sb.charAt(pos))) {
                sb.setCharAt(pos, Character.toUpperCase(sb.charAt(pos)));
                capitalize = false;
            }
            pos++;
        }

        return sb.toString();
    }
}

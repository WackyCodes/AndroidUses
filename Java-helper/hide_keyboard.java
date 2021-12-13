
// Show Or Hide Keyboard ...
    private void showHideKeyBord(View view, boolean isHide) {
        if (isHide) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //Hide..!
            imm.hideSoftInputFromWindow( view.getWindowToken(), 0 );
//            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//            This Method do just opposite! If Input visible, it'll hide and vice-versa.! So Don't Use this
        } else {
            //Show..!
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT );
        }
    }


// Ime Option -> Serch Action Listener...
        editTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // performSearch();
                return true;
            }
            return false;
        });


 
/* TODO : Add Dependency...
    implementation 'com.wdullaer:materialdatetimepicker:4.2.3'
    
    Doc URL : https://github.com/wdullaer/MaterialDateTimePicker#additional-options
*/

// TODO : Check Date Picker Dialog ? Whether it is working or not!
   public class SelectDate extends DialogFragment implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
        public static final int CODE_TO_SELECT_ORIGIN = 1;
        public static final int CODE_TO_SELECT_DESTINATION = 2;
        int queryCode = -1;

        // daysForBookings = [1,2,3,4]

        public SelectDate(int queryCode) {
            this.queryCode = queryCode;
        }

        @Override
        public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int month, int day) {

                    String s = day + "-" + (month + 1) + "-" + year;
                    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
                    Date temp1 = f.parse(s);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.MILLISECOND, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);

//                   Checking is Date privous day from current date ?
                    if (temp1.getTime() < calendar.getTimeInMillis()) {
                        Log.d("time", temp1.getTime() + ", " + calendar.getTimeInMillis());
                        return;
                    }

                    s = f.format(temp1);
          
          // TODO : Set Data...!

        }

        public com.wdullaer.materialdatetimepicker.date.DatePickerDialog getCustomDialog() {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            com.wdullaer.materialdatetimepicker.date.DatePickerDialog
                    dialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                    this,
                    year, // Initial year selection
                    month, // Initial month selection
                    day // Inital day selection
            );

            if (journeyFromToDateFirst != null) {
                try {
                    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//                    Date fromDate = f.parse("2020-06-22T00:00:00");  // Temp!
                    Date fromDate = c.getTime();  // Temp!
                    Date toDate = c.getTime();
                    fromDate = f.parse(journeyFromToDateFirst.getJourneyfromDt());
                    toDate = f.parse(journeyFromToDateFirst.getJourneyToDt());
                    

                    Calendar fromDateCal = Calendar.getInstance();
                    fromDateCal.setTime(fromDate);
                    Calendar toDateCal = Calendar.getInstance();
                    toDateCal.setTime(toDate);

                    dialog.setMinDate(fromDateCal);
                    dialog.setMaxDate(toDateCal);

                    List<Calendar> disabledDays = new ArrayList<>();
                    //Disable all SUNDAYS and SATURDAYS between Min and Max Dates
                    for (Calendar loopdate = fromDateCal;
                         fromDateCal.before(toDateCal) || fromDateCal.getTime().getDate() == toDateCal.getTime().getDate();
                         fromDateCal.add(Calendar.DATE, 1) ) {
                        loopdate = fromDateCal;
                        int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
                        if ( isMatched( dayOfWeek ) ) {
                            disabledDays.add((Calendar)loopdate.clone());
                        }
                    }

                    Calendar[] activeDays = disabledDays.toArray(new Calendar[disabledDays.size()]);
                    dialog.setSelectableDays( activeDays );

                } catch (Exception e) {
//                    Snackbar.make(coordinatorLayout, getString(R.string.error_try_later), Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(activity, getString(R.string.error_try_later), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            } else {
                c.setTimeInMillis(c.getTimeInMillis() - 1000);
                dialog.setMinDate(c);
            }
            return dialog;
        }

        private boolean isMatched( int dayID ){
            boolean isMatch = false;
            for ( int dayKey : daysForBookings ){
                if (dayID == dayKey){
                    isMatch = true;
                    break;
                }
            }
            return isMatch;
        }

    }

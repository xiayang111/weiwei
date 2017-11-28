package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneGetAccountBalanceResult extends BaseResult {
		private ArrayList<BalanceLogs> balanceLogs;
		private float balance;
		private int listNumber;
		

		public float getBalance() {
			return balance;
		}


		public void setBalance(float balance) {
			this.balance = balance;
		}


		public int getListNumber() {
			return listNumber;
		}


		public void setListNumber(int listNumber) {
			this.listNumber = listNumber;
		}


		public ArrayList<BalanceLogs> getBalanceLogs() {
			return balanceLogs;
		}


		public void setBalanceLogs(ArrayList<BalanceLogs> balanceLogs) {
			this.balanceLogs = balanceLogs;
		}


		public class BalanceLogs{
			private String logtime;
			private String moratorium;
			private String paysystemname;
			private String osn;
			
			public String getMoratorium() {
				return moratorium;
			}

			public void setMoratorium(String moratorium) {
				this.moratorium = moratorium;
			}

			public String getPaysystemname() {
				return paysystemname;
			}

			public void setPaysystemname(String paysystemname) {
				this.paysystemname = paysystemname;
			}

			public String getOsn() {
				return osn;
			}

			public void setOsn(String osn) {
				this.osn = osn;
			}

			public String getLogtime() {
				return logtime;
			}

			public void setLogtime(String logtime) {
				  Pattern pattern = Pattern.compile("\\d+");
			        Matcher matcher = pattern.matcher(logtime);
			        if(matcher.find()){
			            this.logtime=matcher.group();
			            return;
			        }
			        this.logtime = logtime;
			}
			
		}
}

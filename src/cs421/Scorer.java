package cs421;

public class Scorer {
	
	public float Score1a(String spell){
		int total = Integer.parseInt(spell.split("_")[1]);
		int errors = Integer.parseInt(spell.split("_")[0]);
		float score = 5.00f*(total - errors)/total;
		return score;
	}
	
	public float Score1b(int errors, int num){
		float score = 5.00f*(num - errors)/num;
		return score;
	}
	
	public float Score1c(int errors, int num){
		float score = 5.00f*(num - errors)/num;
		return score;
	}
	
	public float Score1d(int errors, int num){
		float score = 5.00f*(num - errors)/num;
		return score;
	}
	
	public float Score3a(int num_sentences, float average){
		float score = 0.0f;
		if(num_sentences > 10){
			score = 5.00f;
		}
		else{
			score = 5.00f*(average - num_sentences)/average;
		}
		return score;
	}
	
	public String finalTag(float score, float b2, float a3){
		if(score < 49.52){
			if(b2 > 4.30 && b2 < 4.36){
				return "medium";
			}
			else if(b2 > 4.36){
				return "high";
			}
			else{
				return "low";
			}
		}else if(score >= 49.52 && score < 51.44){
			if(b2 < 4.36 && b2 > 4.30){
				return "medium";
			}
			else if(b2 < 4.30){
				return "low"; 
			}
		}
		return "high";
	}
}

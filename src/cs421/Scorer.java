package cs421;

public class Scorer {
	
	public float Score1a(String spell){
		int total = Integer.parseInt(spell.split("_")[1]);
		int errors = Integer.parseInt(spell.split("_")[0]);
		float score = 5*(total - errors)/total;
		return score;
	}
	
	public float Score1b(int errors, int num){
		float score = 5*(num - errors)/num;
		return score;
	}
	
	public float Score1c(int errors, int num){
		float score = 5*(num - errors)/num;
		return score;
	}
	
	public float Score3a(int num_sentences, float average){
		float score = 0.0f;
		if(num_sentences > 10){
			score = 5.0f;
		}
		else{
			score = 5*(average - num_sentences)/average;
		}
		return score;
	}
	
	public String finalTag(float score, float a3){
		if(score < 18.64){
			if(a3 < 5.0){
				return "low";
			}
			else{
				return "medium";
			}
		}else if(score > 18.64 && score < 20.0){
			if(a3 <= 5.0){
				return "medium";
			}
		}
		return "high";
	}
}

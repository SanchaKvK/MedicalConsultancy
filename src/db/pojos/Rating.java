package db.pojos;

import java.io.Serializable;

public class Rating implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3167341965153243574L;
	private Integer score;
	private String review;
	private Doctor doc;
	private Patient pat;
	
	
	public Rating() {
		super();
		
	}


	public Rating(Doctor doc, Patient pat,Integer score, String review) {
		super();
		this.score = score;
		this.review = review;
		this.doc=doc;
		this.pat=pat;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((review == null) ? 0 : review.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rating other = (Rating) obj;
		if (review == null) {
			if (other.review != null)
				return false;
		} else if (!review.equals(other.review))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		return true;
	}


	public Integer getScore() {
		return score;
	}


	public void setScore(Integer score) {
		this.score = score;
	}


	public String getReview() {
		return review;
	}


	public void setReview(String review) {
		this.review = review;
	}


	@Override
	public String toString() {
		return "Rating [score=" + score + ", review=" + review + "]";
	}


	public Doctor getDoc() {
		return doc;
	}


	public void setDoc(Doctor doc) {
		this.doc = doc;
	}


	public Patient getPat() {
		return pat;
	}


	public void setPat(Patient pat) {
		this.pat = pat;
	}
	
	
	
	
	
	

}

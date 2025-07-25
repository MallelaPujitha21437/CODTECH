package Task4;


import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class RecommendationEngine {
    public static void main(String[] args) {
        try {
            // Load data from a CSV file: userID, itemID, preference (e.g., rating)
            DataModel model = new FileDataModel(new File("data.csv"));

            // Similarity measure between users
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Define neighborhood size (e.g., 2 nearest users)
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Build the recommender
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend items for a user (e.g., user ID 1)
            recommender.recommend(1, 3).forEach(recommendation ->
                System.out.println("Recommended Item ID: " + recommendation.getItemID() +
                        " , Value: " + recommendation.getValue()));
        } catch (IOException | TasteException e) {
            e.printStackTrace();
        }

    }
}

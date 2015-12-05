package classifiers;

import java.util.ArrayList;
import java.util.Arrays;
import misc.AttributeIterator;
import misc.AttributeValidator;
import misc.IPerceptron;
import misc.InvalidAttributesException;
import misc.PerceptronClassifier;
import misc.PerceptronTrainer;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author 6523617
 */
public class LinearPerceptron implements IPerceptron {

    private double bias;
    private double learningRate;
    private final ArrayList<Double> weights;
    private boolean memberOfEssemble;
    private int[] indexes;

    public LinearPerceptron() {
        this.weights = new ArrayList<>();
        this.bias = 1;
        this.learningRate = 1;

    }

    @Override
    public void buildClassifier(Instances instances) throws Exception {
        this.memberOfEssemble = false;
        
        if (!AttributeValidator.validateAttributes(instances)) {
            throw new InvalidAttributesException();
        }

        for (int count = instances.numAttributes() - 1; count > 0; count--) {
            weights.add(1.0);
        }

        PerceptronTrainer.online(instances, this);
    }
    
    public void buildClassifier(AttributeIterator instances) throws Exception {
        this.memberOfEssemble = true;
        
        for (int count = instances.numAttributes() - 1; count >= 0; count--) {
            weights.add(1.0);
        }

        PerceptronTrainer.online(instances, this);
    }
    
    @Override
    public void setIndexes(int[] indexes) {
        this.indexes = indexes;
    }

    @Override
    public double classifyInstance(Instance instnc) throws Exception {
        if (!this.memberOfEssemble) {
            return PerceptronClassifier.classifyInstance(instnc, weights);
        } else {
            // select attrs
            
            double[] values = Arrays.stream(this.indexes).mapToDouble((index) -> instnc.value(index)).toArray();
            
            return PerceptronClassifier.classifyInstance(values, weights);
        } 
    }

    @Override
    public double[] distributionForInstance(Instance instnc) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Capabilities getCapabilities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Double> getWeights() {
        return weights;
    }

    @Override
    public double getBias() {
        return bias;
    }

    @Override
    public void setBias(double bias) {
        this.bias = bias;
    }

    @Override
    public double getLearningRate() {
        return learningRate;
    }

    @Override
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
}

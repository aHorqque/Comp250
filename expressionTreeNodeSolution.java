
class expressionTreeNodeSolution {
    private String value;
    private expressionTreeNodeSolution leftChild, rightChild, parent;
    
    expressionTreeNodeSolution() {
        value = null; 
        leftChild = rightChild = parent = null;
    }
    
    // Constructor
    /* Arguments: String s: Value to be stored in the node
                  expressionTreeNodeSolution l, r, p: the left child, right child, and parent of the node to created      
       Returns: the newly created expressionTreeNodeSolution               
    */
    expressionTreeNodeSolution(String s, expressionTreeNodeSolution l, expressionTreeNodeSolution r, expressionTreeNodeSolution p) {
        value = s; 
        leftChild = l; 
        rightChild = r;
        parent = p;
    }
    
    /* Basic access methods */
    String getValue() { 
    	return value; }

    expressionTreeNodeSolution getLeftChild() { 
    	return leftChild; }

    expressionTreeNodeSolution getRightChild() { 
    	return rightChild; }

    expressionTreeNodeSolution getParent() { 
    	return parent; }

    /* Basic setting methods */ 
    void setValue(String o) { 
    	value = o; }
    
    // sets the left child of this node to n
    void setLeftChild(expressionTreeNodeSolution n) { 
        leftChild = n; 
        n.parent = this; 
    }
    
    // sets the right child of this node to n
    void setRightChild(expressionTreeNodeSolution n) { 
        rightChild = n; 
        n.parent=this; 
    }
    
    // Returns the root of the tree describing the expression s
    // Watch out: it makes no validity checks whatsoever!
    expressionTreeNodeSolution(String s) {
        // check if s contains parentheses. If it doesn't, then it's a leaf
        if (s.indexOf("(")==-1) setValue(s);
        else {  // it's not a leaf

            /* break the string into three parts: the operator, the left operand,
               and the right operand. ***/
            setValue( s.substring( 0 , s.indexOf( "(" ) ) );
            // delimit the left operand 2008
            int left = s.indexOf("(")+1;
            int i = left;
            int parCount = 0;
            // find the comma separating the two operands
            while (parCount>=0 && !(s.charAt(i)==',' && parCount==0)) {
                if ( s.charAt(i) == '(' ) parCount++;
                if ( s.charAt(i) == ')' ) parCount--;
                i++;
            }
            int mid=i;
            if (parCount<0) mid--;

        // recursively build the left subtree
            setLeftChild(new expressionTreeNodeSolution(s.substring(left,mid)));
    
            if (parCount==0) {
                // it is a binary operator
                // find the end of the second operand.F13
                while ( ! (s.charAt(i) == ')' && parCount == 0 ) )  {
                    if ( s.charAt(i) == '(' ) parCount++;
                    if ( s.charAt(i) == ')' ) parCount--;
                    i++;
                }
                int right=i;
                setRightChild( new expressionTreeNodeSolution( s.substring( mid + 1, right)));
        }
    }
    }

    // Returns a copy of the subtree rooted at this node... 2014
    expressionTreeNodeSolution deepCopy() {
        expressionTreeNodeSolution n = new expressionTreeNodeSolution();
        n.setValue( getValue() );
        if ( getLeftChild()!=null ) n.setLeftChild( getLeftChild().deepCopy() );
        if ( getRightChild()!=null ) n.setRightChild( getRightChild().deepCopy() );
        return n;
    }
    
    // Returns a String describing the subtree rooted at a certain node.
    public String toString() {
        String ret = value;
        if ( getLeftChild() == null ) return ret;
        else ret = ret + "(" + getLeftChild().toString();
        if ( getRightChild() == null ) return ret + ")";
        else ret = ret + "," + getRightChild().toString();
        ret = ret + ")";
        return ret;
    } 

    // Returns the value of the expression rooted at a given node
    // when x has a certain value
    double evaluate(double x) {
    	double value = 0.0;
    		//at each of these statements we are checking what operation we are using
    		//and we will apply it to its left and right child evaluating each of those at x
    		//in the case of cosx/sinx/e^x, it is only applied to one variable, 
    		//so we are assuming that it was placed on the left child
    		if(this.value.equals("x")){ //if the node = x, we return the value of x we want
    			value = x;
    		}
    		else if(this.value.equals("add")){ 
    			value = this.leftChild.evaluate(x) + this.rightChild.evaluate(x);
    		}
    		else if(this.value.equals("minus")){
    			value = this.leftChild.evaluate(x) - this.rightChild.evaluate(x);
    		}
    		else if(this.value.equals("mult")){
    			value = this.leftChild.evaluate(x) * this.rightChild.evaluate(x);
    		}
    		else if(this.value.equals("cos")){ 
    			value = Math.cos(this.leftChild.evaluate(x)); 
    		}
    		else if(this.value.equals("sin")){
    			value = Math.sin(this.leftChild.evaluate(x)); 
    		}
    		else if(this.value.equals("exp")){
    			value = Math.pow(2.71828182845904523,this.leftChild.evaluate(x));
    		}
    		else{ //if the node is none of the above, then it must be an integer
    			//so we convert it to int and return that value
    			value = Integer.parseInt(this.value);
    		}
    	return value;
    }                                                 

    /* returns the root of a new expression tree representing the derivative of the
       original expression */
    expressionTreeNodeSolution differentiate() {
    	
		//at each of these statements we are checking what operation we are using
		//and we will apply it to its left and right child evaluating each of those at x
		//in the case of cosx/sinx/e^x, it is only applied to one variable, 
		//so we are assuming that it was placed on the left child
        expressionTreeNodeSolution subTree = new expressionTreeNodeSolution((""));
       	
        if(this.value.equals("x")){ //if the node = x, we return 1
        	setValue("1");
			subTree = deepCopy();
		}
        else if(this.value.equals("add")){ 
        	//we just need to differentiate the left and right children
			setLeftChild(leftChild.deepCopy().differentiate());
			setRightChild(rightChild.deepCopy().differentiate());
			subTree = deepCopy();
		}
		else if(this.value.equals("minus")){ //same process as addition
			setLeftChild(leftChild.deepCopy().differentiate());
			setRightChild(rightChild.deepCopy().differentiate());
			subTree = deepCopy();
		}
		else if(this.value.equals("mult")){
			//We will create two extra temp nodes, and in the right one we will store
			// df/dx*g and on the right f*dg/dx
			expressionTreeNodeSolution tempRight = new expressionTreeNodeSolution(deepCopy().toString());
			expressionTreeNodeSolution tempLeft = new expressionTreeNodeSolution(deepCopy().toString());
			
			//we set the leftChild to be the derivative of f, leaving the right child the same
			tempLeft.setLeftChild(deepCopy().leftChild.differentiate());
			//we set the Rightchild to be derivative of g, leaving the left child the same
			//to obtain mult(f, dgdx)
			tempRight.setRightChild(deepCopy().rightChild.differentiate());
			setRightChild(tempRight);
			setLeftChild(tempLeft);
			setValue("add");
			subTree = deepCopy();
		}
		else if(this.value.equals("cos")){ 
			// we create three extra temporary trees, that will help. the temp will be
			// used to store the same expression but with sin, then we will store in 
			// subtreeCos the derivative of cos if its derivative was just sin
			// then we will have our final tree where minus is the root so we can
			// do 0 - subtreeCos, to obtain a negative result (dcosx/dx = -sinx)
			expressionTreeNodeSolution temp = new expressionTreeNodeSolution(deepCopy().toString());
			expressionTreeNodeSolution subTreeCos = new expressionTreeNodeSolution(deepCopy().toString());
			expressionTreeNodeSolution zero = new expressionTreeNodeSolution("0");
			
			// change the root from cos to sin - now the copy=sinx, then set it as left child
			// i.e. we now have on the left (sinx) (in subTreeCos)
			// then we set the right child to be the derivative of x, i.e. the left child of the copy
			// and we set the root to be mult. 
			//In the final tree we put minus as root, and set the right child to be subTreeCos
			// since subTreeCos is the derivative if (dcosx/dx = sinx), so we obtain the negative
			
			temp.setValue("sin");
			subTreeCos.setLeftChild(temp.deepCopy());
			subTreeCos.setRightChild(temp.leftChild.differentiate());
			subTreeCos.setValue("mult");
			
			setValue("minus");
			setLeftChild(zero);
			setRightChild(subTreeCos);
			subTree = deepCopy();
						
		}
		else if(this.value.equals("sin")){
			expressionTreeNodeSolution temp = new expressionTreeNodeSolution(deepCopy().toString());
			// derivative is mult(cosx, d/dx), so we just copy the original tree
			// change the root from sin to cos -now the copy=cosx, then set it as left child
			// i.e. we now have on the left (cosx), then we set the right child to be
			// the derivative of the left child of the copy (i.e. x) and change root to mult
			temp.setValue("cos");
			setLeftChild(temp.deepCopy());
			setRightChild(temp.leftChild.differentiate());
			setValue("mult");
			
			subTree = deepCopy();
		}
		else if(this.value.equals("exp")){
			expressionTreeNodeSolution temp = new expressionTreeNodeSolution(deepCopy().toString());
			// derivative is mult(exp(x), d/dx), so we just take the original
			// exp set as left child, and multiply by the derivative of the 
			// left child of the exp. then we set the root to mult
			setLeftChild(temp.deepCopy());
			setRightChild(temp.leftChild.differentiate());
			setValue("mult");
			
			subTree = deepCopy();
		}
		else{ //if the node is none of the above, then it must be a constant, so return 0
			setValue("0");
			subTree = deepCopy();
		}
		return subTree;
    }
        
    
    public static void main(String args[]) {
        expressionTreeNodeSolution e = new expressionTreeNodeSolution(("exp(add(2,x))"));
        System.out.println(e.evaluate(1));
        System.out.println(e.differentiate());
  
   
 }
}
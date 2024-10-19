package venom;

import java.util.ArrayList;

/**
 * The Venom class represents a binary search tree of SymbioteHost objects.
 * Venom is a sentient alien symbiote with a liquid-like form that requires a
 * host to bond with for its survival. The host is granted superhuman abilities
 * and the symbiote gains a degree of autonomy. The Venom class contains methods
 * that will help venom find the most compatible host. You are Venom.
 * 
 * @author Ayla Muminovic
 * @author Shane Haughton
 * @author Elian D. Deogracia-Brito
 */
public class Venom {
    private SymbioteHost root;

    /**
     * DO NOT EDIT THIS METHOD
     * 
     * Default constructor.
     */
    public Venom() {
        root = null;
    }

    /**
     * This method is provided to you
     * Creates an array of SymbioteHost objects from a file. The file should
     * contain the number of people on the first line, followed by the name,
     * compatibility, stability, and whether they have antibodies on each
     * subsequent line.
     * 
     * @param filename the name of the file
     * @return an array of SymbioteHosts (should not contain children)
     */
    public SymbioteHost[] createSymbioteHosts(String filename) {
        // DO NOT EDIT THIS METHOD
        StdIn.setFile(filename);
        int numOfPeople = StdIn.readInt();
        SymbioteHost[] people = new SymbioteHost[numOfPeople];
        for (int i = 0; i < numOfPeople; i++) {
            StdIn.readLine();
            String name = StdIn.readLine();
            int compatibility = StdIn.readInt();
            int stability = StdIn.readInt();
            boolean hasAntibodies = StdIn.readBoolean();
            SymbioteHost newPerson = new SymbioteHost(name, compatibility, stability, hasAntibodies, null, null);
            people[i] = newPerson;
        }
        return people;
    }

    /**
     * Inserts a SymbioteHost object into the binary search tree.
     * 
     * @param symbioteHost the SymbioteHost object to insert
     */
    public void insertSymbioteHost(SymbioteHost symbioteHost) {
        root = insertHelper(root, symbioteHost);
    }

    private SymbioteHost insertHelper(SymbioteHost node, SymbioteHost symbioteHost) {
        if (node == null) {
            return symbioteHost;
        }
        int cmp = symbioteHost.getName().compareTo(node.getName());
        if (cmp < 0) {
            node.setLeft(insertHelper(node.getLeft(), symbioteHost));
        } else if (cmp > 0) {
            node.setRight(insertHelper(node.getRight(), symbioteHost));
        } else {
            node.setSymbioteCompatibility(symbioteHost.getSymbioteCompatibility());
            node.setMentalStability(symbioteHost.getMentalStability());
            node.setHasAntibodies(symbioteHost.hasAntibodies());
        }
        return node;
    }

    /**
     * Builds a binary search tree from an array of SymbioteHost objects.
     * 
     * @param filename filename to read
     */
    public void buildTree(String filename) {
        SymbioteHost[] hosts = createSymbioteHosts(filename);
        for (SymbioteHost host : hosts) {
            insertSymbioteHost(host);
        }
    }

    /**
     * Finds the most compatible host in the tree. The most compatible host
     * is the one with the highest suitability.
     * PREorder traversal is used to traverse the tree. The host with the highest
     * suitability
     * is returned. If the tree is empty, null is returned.
     * 
     * USE the calculateSuitability method on a SymbioteHost instance to get
     * a host's suitability.
     * 
     * @return the most compatible SymbioteHost object
     */
    public SymbioteHost findMostSuitable() {
        return findMostSuitableHelper(root, null);
    }

    private SymbioteHost findMostSuitableHelper(SymbioteHost node, SymbioteHost currentBest) {
        if (node == null) {
            return currentBest;
        }
        if (currentBest == null || node.calculateSuitability() > currentBest.calculateSuitability()) {
            currentBest = node;
        }
        currentBest = findMostSuitableHelper(node.getLeft(), currentBest);
        currentBest = findMostSuitableHelper(node.getRight(), currentBest);
        return currentBest;
    }

    /**
     * Finds all hosts in the tree that have antibodies. INorder traversal is used
     * to
     * traverse the tree. The hosts that have antibodies are added to an
     * ArrayList. If the tree is empty, null is returned.
     * 
     * @return an ArrayList of SymbioteHost objects that have antibodies
     */
    public ArrayList<SymbioteHost> findHostsWithAntibodies() {
        ArrayList<SymbioteHost> hosts = new ArrayList<>();
        findHostsWithAntibodiesHelper(root, hosts);
        return hosts;
    }

    private void findHostsWithAntibodiesHelper(SymbioteHost node, ArrayList<SymbioteHost> list) {
        if (node == null) {
            return;
        }
        findHostsWithAntibodiesHelper(node.getLeft(), list);
        if (node.hasAntibodies()) {
            list.add(node);
        }
        findHostsWithAntibodiesHelper(node.getRight(), list);
    }

    /**
     * Finds all hosts in the tree that have a suitability between the given
     * range. The range is inclusive. Level order traversal is used to traverse the
     * tree. The
     * hosts that fall within the range are added to an ArrayList. If the tree
     * is empty, null is returned.
     * 
     * @param minSuitability the minimum suitability
     * @param maxSuitability the maximum suitability
     * @return an ArrayList of SymbioteHost objects that fall within the range
     */
    public ArrayList<SymbioteHost> findHostsWithinSuitabilityRange(int minSuitability, int maxSuitability) {
        if (root == null) {
            return null;
        }
        ArrayList<SymbioteHost> suitable = new ArrayList<>();
        Queue<SymbioteHost> queue = new Queue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            SymbioteHost node = queue.dequeue();
            int suitability = node.calculateSuitability();
            if (suitability >= minSuitability && suitability <= maxSuitability) {
                suitable.add(node);
            }
            if (node.getLeft() != null) {
                queue.enqueue(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.enqueue(node.getRight());
            }
        }
        return suitable;
    }

    /**
     * Deletes a node from the binary search tree with the given name.
     * If the node is not found, nothing happens.
     * 
     * @param name the name of the SymbioteHost object to delete
     */
    public void deleteSymbioteHost(String name) {
        root = deleteHelper(root, name);
    }

    private SymbioteHost deleteHelper(SymbioteHost node, String name) {
        if (node == null) {
            return null;
        }
        int cmp = name.compareTo(node.getName());
        if (cmp < 0) {
            node.setLeft(deleteHelper(node.getLeft(), name));
        } else if (cmp > 0) {
            node.setRight(deleteHelper(node.getRight(), name));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            }
            if (node.getRight() == null) {
                return node.getLeft();
            }
            SymbioteHost minRight = getMin(node.getRight());
            minRight.setRight(deleteMin(node.getRight()));
            minRight.setLeft(node.getLeft());
            node = minRight;
        }
        return node;
    }

    private SymbioteHost getMin(SymbioteHost node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    private SymbioteHost deleteMin(SymbioteHost node) {
        if (node.getLeft() == null) {
            return node.getRight();
        }
        node.setLeft(deleteMin(node.getLeft()));
        return node;
    }

    /**
     * Challenge - worth zero points
     *
     * Heroes have arrived to defeat you! You must clean up the tree to
     * optimize your chances of survival. You must remove hosts with a
     * suitability between 0 and 100 and hosts that have antibodies.
     * 
     * Cleans up the tree by removing nodes with a suitability of 0 to 100
     * and nodes that have antibodies (IN THAT ORDER).
     */
    public void cleanupTree() {
        // WRITE YOUR CODE HERE
    }

    /**
     * Gets the root of the tree.
     * 
     * @return the root of the tree
     */
    public SymbioteHost getRoot() {
        return root;
    }

    /**
     * Prints out the tree.
     */
    public void printTree() {
        if (root == null) {
            return;
        }

        // Modify no. of '\t' based on depth of node
        printTree(root, 0, false, false);
    }

    private void printTree(SymbioteHost root, int depth, boolean isRight, boolean isLeft) {
        System.out.print("\t".repeat(depth));

        if (isRight) {
            System.out.print("|-R- ");
        } else if (isLeft) {
            System.out.print("|-L- ");
        } else {
            System.out.print("+--- ");
        }

        if (root == null) {
            System.out.println("null");
            return;
        }

        System.out.println(root);

        if (root.getLeft() == null && root.getRight() == null) {
            return;
        }

        printTree(root.getLeft(), depth + 1, false, true);
        printTree(root.getRight(), depth + 1, true, false);
    }
}

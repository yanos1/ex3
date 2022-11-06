#################################################################
# FILE : ex3.py
# WRITER : Yan Nosrati, 318862968, yan_won
# EXERCISE : intro2cs2 ex3 2022
# DESCRIPTION: A program demonstrating multiple loops
# STUDENTS I DISCUSSED THE EXERCISE WITH: None
# WEB PAGES I USED: None
#################################################################


def input_list():
    """
    This function creates a list of n numbers given by the user, also
    calculates the sum of inputs
    """
    list_of_inputs = []
    sum_of_inputs = 0
    inputs = True

    while inputs:
        user_input = input()
        if len(user_input) > 0:
            list_of_inputs.append(float(user_input))
            sum_of_inputs += float(user_input)

        else:
            list_of_inputs.append(sum_of_inputs)
            break

    return list_of_inputs


def inner_product(vec_1, vec_2):
    """This function calculates the inner sum of 2 vectors"""
    position = 0
    sum_of_vec = 0

    if len(vec_1) != len(vec_2):
        return

    elif len(vec_1) == 0 and len(vec_2) == 0:
        return 0

    for num in vec_1:
        positional_sum = num * vec_2[position]
        sum_of_vec += positional_sum
        position += 1

    return sum_of_vec


def sequence_monotonicity(sequence):
    """
    This function checks whether a list is ascending or descending
    """
    previous = None
    list_of_bool = [True, True, True, True]  # default
    for num in sequence:
        if not previous:
            previous = num
        else:
            if num > previous:
                list_of_bool[2:4] = False, False
            if num < previous:
                list_of_bool[0:2] = False, False
            if num == previous:
                list_of_bool[1:4:2] = False, False
            previous = num

    return list_of_bool


def monotonicity_inverse(def_bool):
    """
    This function gives examples of ascending and descending lists.
    """
    if def_bool == [True, True, False, False]:
        return [1, 2, 3, 4]
    elif def_bool == [True, False, False, False]:
        return [1, 2, 2, 3]
    elif def_bool == [False, False, True, True]:
        return [4, 2, 1, 0]
    elif def_bool == [False, False, False, False]:
        return [1, 3, 1, 2]
    elif def_bool == [True, False, True, False]:
        return [1, 1, 1, 1]
    elif def_bool == [False, False, True, False]:
        return [4, 3, 3, 1]
    else:
        return None


def convolve(mat):
    convolve_list = []
    cur_list = []
    cur_sum = 0
    i = 0
    j = 0
    while i +2!= len(mat) and j+2!=len(mat[0]):
        for k in range(i, i+3):
            for m in range(j, j+3):
                cur_sum += mat[k][m]
        j+=1
        cur_list.append(float(cur_sum))
        if j+2 == len(mat[0]) and i+2 != len(mat):
            i+=1
            j=0
            convolve_list.append(cur_list)
            cur_list = []
        cur_sum=0
    return convolve_list


def sum_of_vectors(vec_lst):
    """
    This function returns the sum of two vectors.
    """
    list_of_sum = []
    index_sum = 0
    if not any(vec_lst):
        return

    if vec_lst:
        for j in range(len(vec_lst[0])):
            for i in range(len(vec_lst)):
                index_sum += vec_lst[i][j]
            list_of_sum.append(index_sum)
            index_sum = 0

    if list_of_sum:
        return list_of_sum
    else:
        return

def orthogonal_sum(vec_1, vec_2):
    """
    This function takes 2 vector and calculates the orthogonal sum.
    it helps the next function
    """
    count = 0
    for i in range(len(vec_1)):
        count += vec_1[i] * vec_2[i]

    return count


def num_of_orthogonal(vectors):
    pair_count = 0
    for i in range(len(vectors)):
        for j in range(i+1, len(vectors)):
            if orthogonal_sum(vectors[i], vectors[j]) == 0:  # function call
                pair_count += 1
    return pair_count













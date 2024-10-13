#!/bin/bash

branch="main"
is_need_update=true
is_new=false

# Reading arguments with getopts options
while getopts 'b:un' OPTION; do
    case "$OPTION" in
        b)
            branch=$OPTARG ;;
        u)
            is_need_update=true ;;
        n)
            is_new=true ;;
        *)
            # Print helping message for providing wrong options
            echo "Usage: $0 [-b branch_name] [-u] " >&2
            # Terminate from the script
            exit 1 
    esac
done

path_0="/" 
path_1="/aheena-dependency-versions/"
path_2="/aheena-lib-ui/"

root_branch_0="main"
root_branch_1="main"
root_branch_2="main"

if [[ ($branch != "main" ) ]]; then
root_branch_0=$branch
root_branch_1=$branch
root_branch_2=$branch
fi

if $is_need_update;
then
$(git fetch)
fi

root=$(pwd)

for (( counter=0; counter<=2; counter++ ))
do
path=path_$counter
root_branch=root_branch_$counter

cd_command="cd $root${!path}"
echo $cd_command
$cd_command

if $is_new;
then
checkout_command="git checkout -b ${!root_branch}"
echo $checkout_command
$checkout_command
else
checkout_command="git checkout ${!root_branch}"
echo $checkout_command
$checkout_command
fi

if $is_need_update;
then
pull_command="git pull origin ${!root_branch}"
echo $pull_command
$pull_command
fi

done

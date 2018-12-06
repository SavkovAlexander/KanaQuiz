package com.noprestige.kanaquiz.reference;

import android.content.Context;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import static com.noprestige.kanaquiz.questions.QuestionManagement.HIRAGANA;
import static com.noprestige.kanaquiz.questions.QuestionManagement.KATAKANA;
import static com.noprestige.kanaquiz.questions.QuestionManagement.VOCABULARY;

class ReferencePager extends FragmentPagerAdapter
{
    private List<Integer> tabList;
    private Context context;

    ReferencePager(FragmentManager fm, Context context)
    {
        super(fm);

        tabList = new ArrayList<>(3);
        this.context = context;

        if (OptionsControl.getBoolean(R.string.prefid_full_reference))
        {
            tabList.add(R.string.hiragana);
            tabList.add(R.string.katakana);
            tabList.add(R.string.vocabulary);
        }
        else
        {
            if (HIRAGANA.anySelected())
                tabList.add(R.string.hiragana);
            if (KATAKANA.anySelected())
                tabList.add(R.string.katakana);
            if (VOCABULARY.anySelected())
                tabList.add(R.string.vocabulary);
        }
    }

    @Override
    public Fragment getItem(int position)
    {
        return ReferencePage.newInstance(tabList.get(position));
    }

    @Override
    public int getCount()
    {
        return tabList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return context.getResources().getString(tabList.get(position));
    }
}
